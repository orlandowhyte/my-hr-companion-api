#!/bin/bash
# ==========================================================
# Spring Boot Feature Package Generator (HR Companion Edition)
# Base package: com.hr.companion.api
# Generates Controller, Entity, Request, Response, Service,
# Mapper (as interface), and Repository.
# ==========================================================

if [ -z "$1" ]; then
  echo "Usage: ./generate-spring-files.sh EntityName"
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

    @Column(nullable = false)
    private String name;
}
EOF

# ===== Request DTO =====
cat > "$BASE/$PKG/${NAME}Request.java" <<EOF
package com.hr.companion.api.$PKG;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${NAME}Request {
    private String name;
}
EOF

# ===== Response DTO =====
cat > "$BASE/$PKG/${NAME}Response.java" <<EOF
package com.hr.companion.api.$PKG;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${NAME}Response {
    private UUID id;
    private String name;
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

public interface ${NAME}Mapper {
    static $NAME toEntity(${NAME}Request request) {
        return $NAME.builder()
                .name(request.getName())
                .build();
    }

    static ${NAME}Response toResponse($NAME entity) {
        return ${NAME}Response.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
EOF

# ===== Service =====
cat > "$BASE/$PKG/${NAME}Service.java" <<EOF
package com.hr.companion.api.$PKG;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ${NAME}Service {
    private final ${NAME}Repository repository;

    public ${NAME}Service(${NAME}Repository repository) {
        this.repository = repository;
    }

    public List<$NAME> findAll() {
        return repository.findAll();
    }
}
EOF

# ===== Controller =====
cat > "$BASE/$PKG/${NAME}Controller.java" <<EOF
package com.hr.companion.api.$PKG;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/${lname}s")
@AllArgsConstructor
public class ${NAME}Controller {
    private final ${NAME}Service service;

    @GetMapping
    public List<$NAME> getAll() {
        return service.findAll();
    }
}
EOF

echo "✅ Done! Feature package '$BASE/$PKG' created successfully."
