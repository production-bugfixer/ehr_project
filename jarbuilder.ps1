# Set Java 17 for this session
$env:JAVA_HOME="C:\java\jdk_17"
$env:Path="$env:JAVA_HOME\bin;$env:Path"

# List of your project folders
$projects = @("bloodreport", "doctor", "patient", "microbiology", "hospitalgateway", "registerservice")

foreach ($proj in $projects) {
    Write-Host "`n🔧 Building $proj..." -ForegroundColor Cyan
    Set-Location $proj
    .\mvnw.cmd clean package -DskipTests
    docker build -t $proj-service .
    Set-Location ..
}

Write-Host "`n✅ A