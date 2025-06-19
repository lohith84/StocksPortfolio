# Stocks Portfolio Management System

A comprehensive portfolio management system for tracking stock trades and portfolio performance.

## Features

- Track stock trades (BUY/SELL operations)
- Calculate portfolio holdings and returns
- View trade history
- RESTful API for integration
- Containerized with Docker for easy deployment

## Tech Stack

- **Backend**: Java 23, Spring Boot 3.x
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **Containerization**: Docker
- **API Documentation**: SpringDoc OpenAPI

## Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- Docker (optional, for containerized deployment)

## Getting Started

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd portfolio
   ```

2. **Build the application**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`

### Using Docker

1. **Build the Docker image**
   ```bash
   docker build -t portfolio-app .
   ```

2. **Run the container**
   ```bash
   docker run -p 8080:8080 portfolio-app
   ```

## API Documentation

Once the application is running, you can access the API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Available Endpoints

### Trades
- `GET /api/trades` - Get all trades
- `POST /api/trades` - Create a new trade
- `GET /api/trades/{id}` - Get a specific trade
- `PUT /api/trades/{id}` - Update a trade
- `DELETE /api/trades/{id}` - Delete a trade

### Portfolio
- `GET /api/portfolio` - Get current portfolio
- `GET /api/portfolio/returns` - Get portfolio returns

## Configuration

Application configuration can be found in `src/main/resources/application.yaml`

## Testing

Run the test suite with:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Built with Spring Boot
- Uses H2 Database for development
- Containerized with Docker
