![Static Badge](https://img.shields.io/badge/koshiex-RESTScrapper-RESTScrapper)
![GitHub top language](https://img.shields.io/github/languages/top/koshiex/RESTScrapper)
![GitHub](https://img.shields.io/github/license/koshiex/RESTScrapper)

# REST Web Scrapper

## Overview
REST Scrapper is a web scraper designed to interact through REST APIs. Allows you to extract the HTML of a page or get an array of products from various Russian marketplaces obtained by sorting prices in ascending order for a specific querytasks.

In the future, it is planned to speed up the application, improve search results, add more marketplaces and increase security.



## Features
- Supports multiple REST API endpoints
- Easy configuration and setup
- Built with Kotlin
- Dockerized for seamless deployment

## Current supported marketplaces
- OZON
- DNS

## Dependencies:
- Spring
- Selenium
- HtmlUnit
- [Undetected ChromeDriver for java](https://github.com/mabinogi233/UndetectedChromedriver)

## Getting Started
By default, it works on port 80 via HTTP. HTTPS is not supported
### Prerequisites
- Java 17 or higher
- Docker (optional, for containerized deployment)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/koshiex/RESTScrapper.git
   cd RESTScrapper
   ```
2. Build project using Gradle:
   ```bash
   ./gradlew build
   ```
3. Run the application:
   ```bash
   java -jar build/libs/RESTScrapper.jar
   ```
### Docker Deployment
To run the application using Docker.
1. Build the Docker image:
    ```bash
    docker build -t restscrapper .
    ```
2. Run the Docker container:
     ```bash
    docker run -d -p 80:80 restscrapper
    ```
## API Documentation
### Endpoints
1. **GET /getHtml**
   - **Parameters:**:
     - *url* (string, required) - The URL of the page to be scrapped. Returns its HTML markup.
2. **GET /parseProducts**
   - **Parameters:**
     - *productName* (string, required) - The name of the product to be found on the marketplaces. Returns a JSON array.
### Usage example
1. **getHtml**
  **Request:**
     ```bash
       curl -X GET "http://localhost/getHtml?url=https://github.com"
     ```
2. **parseProducts**
   **Request:**
     ```bash
       curl -X GET "http://localhost/parseProducts?productsName=iphone%2015%20pro%20max"
     ```
   **Answer:**
     ```JSON
     [
        {
            "productName": "6.7\" Смартфон Apple iPhone 15 Pro Max 256 ГБ серый",
            "marketName": "DNS",
            "productPrice": "148 499 ₽",
            "picUrl": "https://c.dns-shop.ru/thumb/st4/fit/200/200/aeec31b3145d343400c611f852f6e512/c293b49608500741b7ae16da5f0d30959fea350bd888f71176202c3ac717ed88.jpg",
            "productUrl": "https://www.dns-shop.ru/product/9b1f702e52aeed20/67-smartfon-apple-iphone-15-pro-max-256-gb-seryj/",
            "sameProductsUrl": "https://www.dns-shop.ru/search/?q=iphone+15+pro+max&order=price-asc",
            "storeURL": "https://www.dns-shop.ru"
        },
        {
            "productName": "Треснувший уродливый черный чехол для телефона Iphone15 Apple 13/14promax XR Spoof 678P Soft SE2",
            "marketName": "Ozon",
            "productPrice": "13 ₽",
            "picUrl": "https://ir-2.ozone.ru/s3/multimedia-1-z/wc350/7025223095.jpg",
            "productUrl": "https://www.ozon.ru/product/tresnuvshiy-urodlivyy-chernyy-chehol-dlya-telefona-iphone15-apple-13-14promax-xr-spoof-678p-soft-se2-1571546398/?asb2=6ilRpUbCRK88uRtR5RdZ1xrrcID8DGk9vHyRneCC6AxZ78bH763i6FZdyI8_omc1&avtc=1&avte=2&avts=1716156768&keywords=iphone+15+pro+max",
            "sameProductsUrl": "https://www.ozon.ru/search/?deny_category_prediction=true&category_was_predicted=false&from_global=true&sorting=price&text=iphone+15+pro+max",
            "storeURL": "https://www.ozon.ru"
        }
      ]
     ```
