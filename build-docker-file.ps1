# Define subprojects
$projects = @("bloodreport", "doctor", "hospitalgateway", "microbiology", "patient", "registerservice")

# Optional Dockerfile template
$templateDockerfile = ".\Dockerfile.template"

foreach ($proj in $projects) {
    Write-Host "Processing $proj..." -ForegroundColor Cyan

    $projPath = Join-Path -Path "." -ChildPath $proj

    # Step 1: Build Maven project
    Write-Host "Building Maven project $proj"
    Push-Location $projPath
    & ..\mvnw clean package -DskipTests
    Pop-Location

    # Step 2: Add Dockerfile if not present
    $dockerfilePath = Join-Path $projPath "Dockerfile"
    if (-not (Test-Path $dockerfilePath)) {
        if (Test-Path $templateDockerfile) {
            Copy-Item -Path $templateDockerfile -Destination $dockerfilePath
            Write-Host "Dockerfile created from template in $proj"
        } else {
            Write-Host "Template Dockerfile not found. Skipping Dockerfile creation for $proj" -ForegroundColor Yellow
        }
    }

    # Step 3: Build Docker image
    Write-Host "Building Docker image for $proj"
    docker build -t "$proj:latest" $projPath
}

Write-Host ""
Write-Host "All services built and Docker images created." -ForegroundColor Green
