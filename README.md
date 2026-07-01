# 🍊 OrangeHRM Hybrid Automation Framework

## 📋 Overview
A comprehensive **Hybrid Test Automation Framework** for OrangeHRM application combining:
- **Page Object Model (POM)** - For better code maintainability
- **Data-Driven Testing** - Using Excel/JSON for test data
- **Keyword-Driven Testing** - Reusable keywords for actions
- **Modular Architecture** - Easy to scale and maintain

## 🏗️ Framework Architecture

```
OrangeHRM-Automation/
│
├── src/
│   ├── main/java/
│   │   ├── base/
│   │   │   ├── BaseTest.java           # Base test class with setup/teardown
│   │   │   └── DriverFactory.java      # WebDriver initialization
│   │   ├── pages/
│   │   │   ├── LoginPage.java          # Login page objects
│   │   │   ├── DashboardPage.java      # Dashboard page objects
│   │   │   ├── PIMPage.java            # PIM module page objects
│   │   │   └── AdminPage.java          # Admin module page objects
│   │   ├── utils/
│   │   │   ├── ExcelUtils.java         # Excel read/write operations
│   │   │   ├── ConfigReader.java       # Configuration file reader
│   │   │   ├── ExtentReportManager.java # Extent report configuration
│   │   │   ├── ScreenshotUtils.java    # Screenshot utilities
│   │   │   └── LoggerUtils.java        # Logging utilities
│   │   └── keywords/
│   │       └── ActionKeywords.java     # Reusable action keywords
│   │
│   └── test/
│       ├── java/
│       │   ├── tests/
│       │   │   ├── LoginTests.java
│       │   │   ├── EmployeeTests.java
│       │   │   └── DataDrivenTests.java
│       │   └── listeners/
│       │       └── TestListener.java
│       └── resources/
│           ├── config/
│           │   └── config.properties   # Application configuration
│           ├── testdata/
│           │   ├── LoginData.xlsx      # Login test data
│           │   └── EmployeeData.xlsx   # Employee test data
│           └── log4j2.xml              # Logging configuration
│
├── reports/                             # Test execution reports
├── screenshots/                         # Failed test screenshots
├── logs/                               # Execution logs
├── testng.xml                          # TestNG suite configuration
└── pom.xml                             # Maven dependencies

```

## 🚀 Features

✅ **Hybrid Framework** - Combines best practices from multiple frameworks  
✅ **Cross-Browser Testing** - Chrome, Firefox, Edge support  
✅ **Parallel Execution** - Run tests in parallel using TestNG  
✅ **Extent Reports** - Beautiful HTML reports with screenshots  
✅ **Data-Driven** - Excel/JSON based test data management  
✅ **Keyword-Driven** - Reusable action keywords  
✅ **Screenshot on Failure** - Automatic screenshot capture  
✅ **Logging** - Comprehensive Log4j logging  
✅ **CI/CD Ready** - Easy integration with Jenkins/GitHub Actions  

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 11+ | Programming Language |
| Selenium WebDriver | 4.15.0 | Browser Automation |
| TestNG | 7.8.0 | Testing Framework |
| Maven | 3.x | Build Tool |
| Extent Reports | 5.1.1 | Reporting |
| Apache POI | 5.2.5 | Excel Operations |
| Log4j | 2.22.0 | Logging |
| WebDriverManager | 5.6.2 | Driver Management |

## 📦 Prerequisites

- Java JDK 11 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browser
- IDE (IntelliJ IDEA / Eclipse)

## ⚙️ Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd OrangeHRM-Automation
```

### 2. Install Dependencies
```bash
mvn clean install -DskipTests
```

### 3. Update Configuration
Edit `src/test/resources/config/config.properties`:
```properties
url=https://opensource-demo.orangehrmlive.com/
browser=chrome
headless=false
timeout=30
```

### 4. Update Test Data
- Navigate to `src/test/resources/testdata/`
- Update `LoginData.xlsx` and `EmployeeData.xlsx` with your test data

## 🏃 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Suite
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Run Tests in Specific Browser
```bash
mvn clean test -Dbrowser=firefox
```

### Run Tests in Headless Mode
```bash
mvn clean test -Dheadless=true
```

### Run Specific Test Class
```bash
mvn clean test -Dtest=LoginTests
```

## 📊 Test Reports

After test execution, reports are generated in:
- **Extent Reports**: `reports/ExtentReport.html`
- **TestNG Reports**: `target/surefire-reports/`
- **Screenshots**: `screenshots/`
- **Logs**: `logs/application.log`

## 📝 Writing Tests

### Example Test Class
```java
@Test(dataProvider = "loginData", dataProviderClass = DataProviders.class)
public void testLogin(String username, String password, String expected) {
    loginPage.login(username, password);
    Assert.assertEquals(dashboardPage.getDashboardTitle(), expected);
}
```

## 🎯 Best Practices Implemented

✅ Separation of concerns (Page Objects, Tests, Utils)  
✅ DRY principle - No code duplication  
✅ Descriptive naming conventions  
✅ Proper exception handling  
✅ Configurable waits (Explicit waits)  
✅ Independent test cases  
✅ Proper test data management  
✅ Comprehensive logging  

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📧 Contact

For queries, contact: your-email@example.com

## 📄 License

This project is licensed under the MIT License.
