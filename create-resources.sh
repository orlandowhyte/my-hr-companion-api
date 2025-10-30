#!/bin/bash
# ==========================================================
# Spring Boot Feature Package Generator (HR Companion Edition)
# Base package: com.hr.companion.api
# Generates Controller, Entity, Request, Response, Service,
# Mapper (as interface), and Repository.
# ==========================================================

if [ -z "$1" ]; then
  echo "Usage: ./create-resources.sh EntityName"
  exit 1
fi

NAME=$1
lname="$(echo "${NAME:0:1}" | tr '[:upper:]' '[:lower:]')${NAME:1}"
PKG=$lname
BASE="src/main/java/com/hr/companion/api"

echo "Creating feature package under $BASE/$PKG"
echo "---------------------------------------------------------"

if [ ! -d "$BASE" ]; then
  echo "Error: Base path $BASE not found. Please run from project root."
  exit 1
fi

mkdir -p "$BASE/$PKG"

# ===== Entity =====
cat > "$BASE/$PKG/$NAME.java" <<EOF
package com.hr.companion.api.$PKG;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "$PKG")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class $NAME {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String status = "active";
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
EOF

# ===== Request DTO =====
cat > "$BASE/$PKG/${NAME}Request.java" <<EOF
package com.hr.companion.api.$PKG;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request Object for ${NAME}")
public class ${NAME}Request {
    @Schema(description = "", example = "")
    @NotBlank(message = "")
    private UUID id;
}
EOF

# ===== Response DTO =====
cat > "$BASE/$PKG/${NAME}Response.java" <<EOF
package com.hr.companion.api.$PKG;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response Object for ${NAME}")
public class ${NAME}Response {
    private UUID id;
    @Schema(description = "Status of ${lname}", example = "active")
    private String status = "active";
    @Schema(description = "Creation date of ${lname}", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime createdAt;
    @Schema(description = "Update date of ${lname}", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime updatedAt;
}
EOF

# ===== Repository =====
cat > "$BASE/$PKG/${NAME}Repository.java" <<EOF
package com.hr.companion.api.$PKG;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ${NAME}Repository extends JpaRepository<$NAME, UUID> {}
EOF

# ===== Mapper (Interface) =====
cat > "$BASE/$PKG/${NAME}Mapper.java" <<EOF
package com.hr.companion.api.$PKG;

import com.hr.companion.api.config.CentralMapperConfig;
import com.hr.companion.api.util.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = CentralMapperConfig.class)
public interface ${NAME}Mapper extends BaseMapper<${NAME}, ${NAME}Request, ${NAME}Response>{}
EOF

# ===== Service =====
cat > "$BASE/$PKG/${NAME}Service.java" <<EOF
package com.hr.companion.api.$PKG;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ${NAME}Service {
    private final ${NAME}Repository ${lname}Repository;
    private final ${NAME}Mapper ${lname}Mapper;


    /**
     * Retrieves all ${lname}s from the database.
     * @return List of ${NAME}s.
     */
    public List<${NAME}Response> getAll${NAME}s() {
        log.info("Returned {} ${lname}s", ${lname}Repository.count());
        return ${lname}Mapper.toResponseList(${lname}Repository.findAll());
    }

    /**
     * Creates a new ${lname} in the database.
     * @param request ${NAME}Request containing details of the ${lname} to be created.
     * @return ${NAME}Response representing the newly created ${lname}.
     */
    public ${NAME}Response create${NAME}(${NAME}Request request) {
        log.info("Creating a new ${lname} with id: {}", request.getId());
        $NAME saved${NAME} = ${lname}Repository.save(${lname}Mapper.toEntity(request));
        log.info("$NAME created with ID: {}", saved${NAME}.getId());
        return ${lname}Mapper.toResponse(saved${NAME});
    }

    /**
     * Retrieves a ${lname} by its ID.
     * @param ${lname}Id UUID representing the unique identifier of the ${lname}.
     * @return ${NAME}Response representing the ${lname} with the specified ID.
     * @throws ${NAME}NotFoundException if the ${lname} is not found.
     */
    public ${NAME}Response get${NAME}ById(UUID ${lname}Id) {
        $NAME ${lname} = ${lname}Repository.findById(${lname}Id)
                .orElseThrow(() -> new ${NAME}NotFoundException("$NAME not found with ID: " + ${lname}Id));
        return ${lname}Mapper.toResponse(${lname});
    }
}
EOF

# ===== Controller =====
cat > "$BASE/$PKG/${NAME}Controller.java" <<EOF
package com.hr.companion.api.$PKG;

import com.hr.companion.api.util.ApiErrorResponse;
import com.hr.companion.api.util.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vi/api/${lname}")
@AllArgsConstructor
@Tag(name="${NAME}", description="Controller that handles ${lname} operations")
public class ${NAME}Controller {
    private final ${NAME}Service ${lname}Service;

    @GetMapping
    @Operation(summary = "Return all ${lname}s", description = "Returns a list of all ${lname}s")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "${NAME}s retrieved successfully"),
    })
    public ResponseEntity<ApiSuccessResponse<List<${NAME}Response>>> getAll${NAME}s() {
        List<${NAME}Response> ${lname}s = ${lname}Service.getAll${NAME}s();
        return ResponseEntity.ok((ApiSuccessResponse.success(${lname}s,
                "List of ${lname}s returned successfully", HttpStatus.OK.value())));
    }

    @PostMapping
    @Operation(summary = "Create a new ${lname}", description = "Creates a new ${lname} with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "${NAME} created successfully"),
    })
    public ResponseEntity<ApiSuccessResponse<${NAME}Response>> create${NAME}(
            @Valid @RequestBody ${NAME}Request request) {
        URI location = URI.create("/v1/api/${lname}");
        ${NAME}Response created${NAME} =  ${lname}Service.create${NAME}(request);
        return ResponseEntity.created(location).body((ApiSuccessResponse.success(created${NAME},
                "${NAME} created successfully", HttpStatus.CREATED.value())));
    }

    @GetMapping("/{${lname}Id}")
    @Operation(summary = "Return ${lname} by Id", description = "Finds and returns ${lname} by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "${NAME} found successfully"),
            @ApiResponse(responseCode = "404", description = "${NAME} not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    public ResponseEntity<ApiSuccessResponse<${NAME}Response>> get${NAME}ById(
            @PathVariable("${lname}Id") String ${lname}Id) {
        ${NAME}Response ${lname} = ${lname}Service.get${NAME}ById(UUID.fromString(${lname}Id));
        return ResponseEntity.ok((ApiSuccessResponse.success(${lname},
                "${NAME} returned successfully", HttpStatus.OK.value())));
    }
}
EOF

echo "✅ Done! Feature package '$BASE/$PKG' created successfully."
