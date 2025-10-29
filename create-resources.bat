@echo off
REM ==========================================================
REM Spring Boot Feature Package Generator (HR Companion Edition)
REM Base package: com.hr.companion.api
REM Generates Controller, Entity, Request, Response, Service,
REM Mapper (as interface), and Repository.
REM ==========================================================

if "%~1"=="" (
    echo Usage: generate-spring-files.bat EntityName
    exit /b
)

set NAME=%~1
for /f "tokens=*" %%a in ('powershell -command "(\"%NAME:~0,1%\".ToLower() + \"%NAME:~1%\")"') do set lname=%%a
set PKG=%lname%
set BASE=src\main\java\com\hr\companion\api

echo Creating feature package under %BASE%\%PKG%
echo ---------------------------------------------------------

if not exist %BASE% (
    echo Error: Base path %BASE% not found. Please run from project root.
    exit /b
)

mkdir %BASE%\%PKG% 2>nul

REM ===== Entity =====
echo package com.hr.companion.api.%PKG%;> %BASE%\%PKG%\%NAME%.java
echo.>> %BASE%\%PKG%\%NAME%.java
echo import jakarta.persistence.*;>> %BASE%\%PKG%\%NAME%.java
echo import lombok.*;>> %BASE%\%PKG%\%NAME%.java
echo import java.util.UUID;>> %BASE%\%PKG%\%NAME%.java
echo.>> %BASE%\%PKG%\%NAME%.java
echo @Entity>> %BASE%\%PKG%\%NAME%.java
echo @Table(name = "%PKG%")>> %BASE%\%PKG%\%NAME%.java
echo @Getter>> %BASE%\%PKG%\%NAME%.java
echo @Setter>> %BASE%\%PKG%\%NAME%.java
echo @Builder>> %BASE%\%PKG%\%NAME%.java
echo @NoArgsConstructor>> %BASE%\%PKG%\%NAME%.java
echo @AllArgsConstructor>> %BASE%\%PKG%\%NAME%.java
echo public class %NAME% {>> %BASE%\%PKG%\%NAME%.java
echo.    @Id>> %BASE%\%PKG%\%NAME%.java
echo.    @GeneratedValue(strategy = GenerationType.UUID)>> %BASE%\%PKG%\%NAME%.java
echo.    private UUID id;>> %BASE%\%PKG%\%NAME%.java
echo.>> %BASE%\%PKG%\%NAME%.java
echo.    @Column(nullable = false)>> %BASE%\%PKG%\%NAME%.java
echo.    private String name;>> %BASE%\%PKG%\%NAME%.java
echo.}>> %BASE%\%PKG%\%NAME%.java

REM ===== Request DTO =====
echo package com.hr.companion.api.%PKG%;> %BASE%\%PKG%\%NAME%Request.java
echo.>> %BASE%\%PKG%\%NAME%Request.java
echo import lombok.*;>> %BASE%\%PKG%\%NAME%Request.java
echo.>> %BASE%\%PKG%\%NAME%Request.java
echo @Data>> %BASE%\%PKG%\%NAME%Request.java
echo @Builder>> %BASE%\%PKG%\%NAME%Request.java
echo @NoArgsConstructor>> %BASE%\%PKG%\%NAME%Request.java
echo @AllArgsConstructor>> %BASE%\%PKG%\%NAME%Request.java
echo public class %NAME%Request {>> %BASE%\%PKG%\%NAME%Request.java
echo.    private String name;>> %BASE%\%PKG%\%NAME%Request.java
echo.}>> %BASE%\%PKG%\%NAME%Request.java

REM ===== Response DTO =====
echo package com.hr.companion.api.%PKG%;> %BASE%\%PKG%\%NAME%Response.java
echo.>> %BASE%\%PKG%\%NAME%Response.java
echo import lombok.*;>> %BASE%\%PKG%\%NAME%Response.java
echo import java.util.UUID;>> %BASE%\%PKG%\%NAME%Response.java
echo.>> %BASE%\%PKG%\%NAME%Response.java
echo @Data>> %BASE%\%PKG%\%NAME%Response.java
echo @Builder>> %BASE%\%PKG%\%NAME%Response.java
echo @NoArgsConstructor>> %BASE%\%PKG%\%NAME%Response.java
echo @AllArgsConstructor>> %BASE%\%PKG%\%NAME%Response.java
echo public class %NAME%Response {>> %BASE%\%PKG%\%NAME%Response.java
echo.    private UUID id;>> %BASE%\%PKG%\%NAME%Response.java
echo.    private String name;>> %BASE%\%PKG%\%NAME%Response.java
echo.}>> %BASE%\%PKG%\%NAME%Response.java

REM ===== Repository =====
echo package com.hr.companion.api.%PKG%;> %BASE%\%PKG%\%NAME%Repository.java
echo.>> %BASE%\%PKG%\%NAME%Repository.java
echo import org.springframework.data.jpa.repository.JpaRepository;>> %BASE%\%PKG%\%NAME%Repository.java
echo import java.util.UUID;>> %BASE%\%PKG%\%NAME%Repository.java
echo.>> %BASE%\%PKG%\%NAME%Repository.java
echo public interface %NAME%Repository extends JpaRepository^<%NAME%, UUID^> {}>> %BASE%\%PKG%\%NAME%Repository.java

REM ===== Mapper (Interface) =====
echo package com.hr.companion.api.%PKG%;> %BASE%\%PKG%\%NAME%Mapper.java
echo.>> %BASE%\%PKG%\%NAME%Mapper.java
echo public interface %NAME%Mapper {>> %BASE%\%PKG%\%NAME%Mapper.java
echo.    static %NAME% toEntity(%NAME%Request request) {>> %BASE%\%PKG%\%NAME%Mapper.java
echo.        return %NAME%.builder()>> %BASE%\%PKG%\%NAME%Mapper.java
echo.            .name(request.getName())>> %BASE%\%PKG%\%NAME%Mapper.java
echo.            .build();>> %BASE%\%PKG%\%NAME%Mapper.java
echo.    }>> %BASE%\%PKG%\%NAME%Mapper.java
echo.>> %BASE%\%PKG%\%NAME%Mapper.java
echo.    static %NAME%Response toResponse(%NAME% entity) {>> %BASE%\%PKG%\%NAME%Mapper.java
echo.        return %NAME%Response.builder()>> %BASE%\%PKG%\%NAME%Mapper.java
echo.            .id(entity.getId())>> %BASE%\%PKG%\%NAME%Mapper.java
echo.            .name(entity.getName())>> %BASE%\%PKG%\%NAME%Mapper.java
echo.            .build();>> %BASE%\%PKG%\%NAME%Mapper.java
echo.    }>> %BASE%\%PKG%\%NAME%Mapper.java
echo.}>> %BASE%\%PKG%\%NAME%Mapper.java

REM ===== Service =====
echo package com.hr.companion.api.%PKG%;> %BASE%\%PKG%\%NAME%Service.java
echo.>> %BASE%\%PKG%\%NAME%Service.java
echo import org.springframework.stereotype.Service;>> %BASE%\%PKG%\%NAME%Service.java
echo import java.util.List;>> %BASE%\%PKG%\%NAME%Service.java
echo.>> %BASE%\%PKG%\%NAME%Service.java
echo @Service>> %BASE%\%PKG%\%NAME%Service.java
echo public class %NAME%Service {>> %BASE%\%PKG%\%NAME%Service.java
echo.    private final %NAME%Repository repository;>> %BASE%\%PKG%\%NAME%Service.java
echo.    public %NAME%Service(%NAME%Repository repository) {>> %BASE%\%PKG%\%NAME%Service.java
echo.        this.repository = repository;>> %BASE%\%PKG%\%NAME%Service.java
echo.    }>> %BASE%\%PKG%\%NAME%Service.java
echo.    public List^<%NAME%^> findAll() {>> %BASE%\%PKG%\%NAME%Service.java
echo.        return repository.findAll();>> %BASE%\%PKG%\%NAME%Service.java
echo.    }>> %BASE%\%PKG%\%NAME%Service.java
echo.}>> %BASE%\%PKG%\%NAME%Service.java

REM ===== Controller =====
echo package com.hr.companion.api.%PKG%;> %BASE%\%PKG%\%NAME%Controller.java
echo.>> %BASE%\%PKG%\%NAME%Controller.java
echo import org.springframework.web.bind.annotation.*;>> %BASE%\%PKG%\%NAME%Controller.java
echo import java.util.List;>> %BASE%\%PKG%\%NAME%Controller.java
echo import lombok.AllArgsConstructor;>> %BASE%\%PKG%\%NAME%Controller.java
echo.>> %BASE%\%PKG%\%NAME%Controller.java
echo @RestController>> %BASE%\%PKG%\%NAME%Controller.java
echo @RequestMapping("/api/%lname%s")>> %BASE%\%PKG%\%NAME%Controller.java
echo @AllArgsConstructor>> %BASE%\%PKG%\%NAME%Controller.java
echo public class %NAME%Controller {>> %BASE%\%PKG%\%NAME%Controller.java
echo.    private final %NAME%Service service;>> %BASE%\%PKG%\%NAME%Controller.java
echo.    @GetMapping>> %BASE%\%PKG%\%NAME%Controller.java
echo.    public List^<%NAME%^> getAll() {>> %BASE%\%PKG%\%NAME%Controller.java
echo.        return service.findAll();>> %BASE%\%PKG%\%NAME%Controller.java
echo.    }>> %BASE%\%PKG%\%NAME%Controller.java
echo.}>> %BASE%\%PKG%\%NAME%Controller.java

echo Done! Feature package "%BASE%\%PKG%" created successfully.
