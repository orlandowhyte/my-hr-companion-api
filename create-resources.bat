@echo off
REM ==========================================================
REM Spring Boot Feature Package Generator (HR Companion Edition)
REM Base package: com.hr.companion.api
REM Generates Controller, Entity, Request, Response, Service,
REM Mapper (as interface), and Repository.
REM ==========================================================

if "%~1"=="" (
  echo Usage: create-resources.bat EntityName
  exit /b 1
)

set NAME=%~1
set lname=%NAME:~0,1%
for %%A in (%lname%) do set lname=%%A
for /f "tokens=1" %%a in ('echo %NAME:~0,1% ^| powershell -Command "(Get-Content -).ToLower()"') do set firstLower=%%a
set lname=%firstLower%%NAME:~1%
set PKG=%lname%
set BASE=src\main\java\com\hr\companion\api

echo Creating feature package under %BASE%\%PKG%
echo ---------------------------------------------------------

if not exist "%BASE%" (
  echo Error: Base path %BASE% not found. Please run from project root.
  exit /b 1
)

if not exist "%BASE%\%PKG%" mkdir "%BASE%\%PKG%"

REM ===== Entity =====
(
echo package com.hr.companion.api.%PKG%;
echo.
echo import jakarta.persistence.*;
echo import lombok.*;
echo import org.hibernate.annotations.CreationTimestamp;
echo import org.hibernate.annotations.UpdateTimestamp;
echo.
echo import java.time.OffsetDateTime;
echo import java.util.UUID;
echo.
echo @Entity
echo @Table(name = "%PKG%")
echo @Getter
echo @Setter
echo @Builder
echo @NoArgsConstructor
echo @AllArgsConstructor
echo public class %NAME% {
echo.    @Id
echo.    @GeneratedValue(strategy = GenerationType.UUID)
echo.    private UUID id;
echo.    private String status = "active";
echo.    @CreationTimestamp
echo.    @Column(name = "created_at", nullable = false, updatable = false)
echo.    private OffsetDateTime createdAt;
echo.    @UpdateTimestamp
echo.    @Column(name = "updated_at", nullable = false)
echo.    private OffsetDateTime updatedAt;
echo }
) > "%BASE%\%PKG%\%NAME%.java"

REM ===== Request DTO =====
(
echo package com.hr.companion.api.%PKG%;
echo.
echo import io.swagger.v3.oas.annotations.media.Schema;
echo import jakarta.validation.constraints.NotBlank;
echo import lombok.AllArgsConstructor;
echo import lombok.Builder;
echo import lombok.Data;
echo import lombok.NoArgsConstructor;
echo.
echo import java.util.UUID;
echo.
echo @Data
echo @Builder
echo @NoArgsConstructor
echo @AllArgsConstructor
echo @Schema(description = "Request Object for %NAME%")
echo public class %NAME%Request {
echo.    @Schema(description = "", example = "")
echo.    @NotBlank(message = "")
echo.    private UUID id;
echo }
) > "%BASE%\%PKG%\%NAME%Request.java"

REM ===== Response DTO =====
(
echo package com.hr.companion.api.%PKG%;
echo.
echo import io.swagger.v3.oas.annotations.media.Schema;
echo import jakarta.validation.constraints.NotBlank;
echo import lombok.AllArgsConstructor;
echo import lombok.Builder;
echo import lombok.Data;
echo import lombok.NoArgsConstructor;
echo.
echo import java.time.OffsetDateTime;
echo import java.util.UUID;
echo.
echo @Data
echo @Builder
echo @NoArgsConstructor
echo @AllArgsConstructor
echo @Schema(description = "Response Object for %NAME%")
echo public class %NAME%Response {
echo.    private UUID id;
echo.    @Schema(description = "Status of %lname%", example = "active")
echo.    private String status = "active";
echo.    @Schema(description = "Creation date of %lname%", example = "2025-10-25 21:45:13.123+00")
echo.    private OffsetDateTime createdAt;
echo.    @Schema(description = "Update date of %lname%", example = "2025-10-25 21:45:13.123+00")
echo.    private OffsetDateTime updatedAt;
echo }
) > "%BASE%\%PKG%\%NAME%Response.java"

REM ===== Repository =====
(
echo package com.hr.companion.api.%PKG%;
echo.
echo import org.springframework.data.jpa.repository.JpaRepository;
echo import java.util.UUID;
echo.
echo public interface %NAME%Repository extends JpaRepository^<%NAME%, UUID^> {}
) > "%BASE%\%PKG%\%NAME%Repository.java"

REM ===== Mapper =====
(
echo package com.hr.companion.api.%PKG%;
echo.
echo import com.hr.companion.api.config.CentralMapperConfig;
echo import com.hr.companion.api.util.BaseMapper;
echo import org.mapstruct.Mapper;
echo.
echo @Mapper(config = CentralMapperConfig.class)
echo public interface %NAME%Mapper extends BaseMapper^<%NAME%, %NAME%Request, %NAME%Response^> {}
) > "%BASE%\%PKG%\%NAME%Mapper.java"

REM ===== Service =====
(
echo package com.hr.companion.api.%PKG%;
echo.
echo import org.springframework.stereotype.Service;
echo import lombok.AllArgsConstructor;
echo import lombok.extern.slf4j.Slf4j;
echo import java.util.List;
echo import java.util.UUID;
echo.
echo @Service
echo @Slf4j
echo @AllArgsConstructor
echo public class %NAME%Service {
echo.    private final %NAME%Repository %lname%Repository;
echo.    private final %NAME%Mapper %lname%Mapper;
echo.
echo.    public List^<%NAME%Response^> getAll%NAME%s() {
echo.        log.info("Returned {} %lname%s", %lname%Repository.count());
echo.        return %lname%Mapper.toResponseList(%lname%Repository.findAll());
echo.    }
echo.
echo.    public %NAME%Response create%NAME%(%NAME%Request request) {
echo.        log.info("Creating a new %lname% with id: {}", request.getId());
echo.        %NAME% saved%NAME% = %lname%Repository.save(%lname%Mapper.toEntity(request));
echo.        log.info("%NAME% created with ID: {}", saved%NAME%.getId());
echo.        return %lname%Mapper.toResponse(saved%NAME%);
echo.    }
echo.
echo.    public %NAME%Response get%NAME%ById(UUID %lname%Id) {
echo.        %NAME% %lname% = %lname%Repository.findById(%lname%Id)
echo.            .orElseThrow(() -> new %NAME%NotFoundException("%NAME% not found with ID: " + %lname%Id));
echo.        return %lname%Mapper.toResponse(%lname%);
echo.    }
echo }
) > "%BASE%\%PKG%\%NAME%Service.java"

REM ===== Controller =====
(
echo package com.hr.companion.api.%PKG%;
echo.
echo import com.hr.companion.api.util.ApiErrorResponse;
echo import com.hr.companion.api.util.ApiSuccessResponse;
echo import io.swagger.v3.oas.annotations.Operation;
echo import io.swagger.v3.oas.annotations.media.Content;
echo import io.swagger.v3.oas.annotations.media.Schema;
echo import io.swagger.v3.oas.annotations.responses.ApiResponse;
echo import io.swagger.v3.oas.annotations.responses.ApiResponses;
echo import io.swagger.v3.oas.annotations.tags.Tag;
echo import jakarta.validation.Valid;
echo import lombok.AllArgsConstructor;
echo import org.springframework.http.HttpStatus;
echo import org.springframework.http.ResponseEntity;
echo import org.springframework.web.bind.annotation.*;
echo.
echo import java.net.URI;
echo import java.util.List;
echo import java.util.UUID;
echo.
echo @RestController
echo @RequestMapping("/v1/api/%lname%")
echo @AllArgsConstructor
echo @Tag(name="%NAME%", description="Controller that handles %lname% operations")
echo public class %NAME%Controller {
echo.    private final %NAME%Service %lname%Service;
echo.
echo.    @GetMapping
echo.    @Operation(summary = "Return all %lname%s", description = "Returns a list of all %lname%s")
echo.    @ApiResponses({
echo.        @ApiResponse(responseCode = "200", description = "%NAME%s retrieved successfully"),
echo.    })
echo.    public ResponseEntity^<ApiSuccessResponse^<List^<%NAME%Response^>^>^> getAll%NAME%s() {
echo.        List^<%NAME%Response^> %lname%s = %lname%Service.getAll%NAME%s();
echo.        return ResponseEntity.ok(ApiSuccessResponse.success(%lname%s,
echo.            "List of %lname%s returned successfully", HttpStatus.OK.value()));
echo.    }
echo.
echo.    @PostMapping
echo.    @Operation(summary = "Create a new %lname%", description = "Creates a new %lname% with the provided details")
echo.    @ApiResponses({
echo.        @ApiResponse(responseCode = "201", description = "%NAME% created successfully"),
echo.    })
echo.    public ResponseEntity^<ApiSuccessResponse^<%NAME%Response^>^> create%NAME%(@Valid @RequestBody %NAME%Request request) {
echo.        URI location = URI.create("/v1/api/%lname%");
echo.        %NAME%Response created%NAME% = %lname%Service.create%NAME%(request);
echo.        return ResponseEntity.created(location).body(ApiSuccessResponse.success(created%NAME%,
echo.            "%NAME% created successfully", HttpStatus.CREATED.value()));
echo.    }
echo.
echo.    @GetMapping("/{%lname%Id}")
echo.    @Operation(summary = "Return %lname% by Id", description = "Finds and returns %lname% by Id")
echo.    @ApiResponses({
echo.        @ApiResponse(responseCode = "200", description = "%NAME% found successfully"),
echo.        @ApiResponse(responseCode = "404", description = "%NAME% not found",
echo.            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
echo.    })
echo.    public ResponseEntity^<ApiSuccessResponse^<%NAME%Response^>^> get%NAME%ById(@PathVariable("%lname%Id") String %lname%Id) {
echo.        %NAME%Response %lname% = %lname%Service.get%NAME%ById(UUID.fromString(%lname%Id));
echo.        return ResponseEntity.ok(ApiSuccessResponse.success(%lname%,
echo.            "%NAME% returned successfully", HttpStatus.OK.value()));
echo.    }
echo }
) > "%BASE%\%PKG%\%NAME%Controller.java"

echo ✅ Done! Feature package "%BASE%\%PKG%" created successfully.
