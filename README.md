<!--
# Microservices with Orchestrator including Saga Pattern Rollback

An example of orchestrating several microservices using event-driven architecture and reactive programming.

In continuous development...

## Requirements

For building and running the application you need:

- [JDK 17 or newer](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [IntelliJ IDE or other Java compatible IDE](https://www.jetbrains.com/idea/)
- [Docker 4.36.0 or newer ](https://www.docker.com/products/docker-desktop/)

## Some useful information

 - Microservices are built using Spring Boot 3 
 - Event-Driven design using Kafka broker and Zookeeper Docker container

## Case Study

An Order Management system uses Microservices to execute and process Order requests. When an Order request is created it stays in Pending status until the payment is executed successfully and product item is deduced from inventory stock with no failures.

![Real case scenario](/images/Real_Case_Scenario.png)

## Running the Microservices

To run the microservices there are 2 options. 

 1. Running the whole system in Docker containers. 
 2. Run only Kafka services on Docker container and launch the Spring Boot
    Microservices from the IDE.

## Running the Microservices on Docker containers

Step 1. Build target .jar files

    /common-orchestration/mvnw clean install
    /inventory-service/mvnw clean installl
    /orchestrator-service/mvnw clean install
    /order-service/mvnw clean install
    /payment-service/mvnw clean install

Step 2. Run Docker Compose file
*from the root directory run command* 

    docker-compose -f docker-compose.yaml up -d --build


## Running the Microservices from IDE 

Step 1. Import project in the IDE 

Step 2. From root directory run command

     docker-compose -f docker-compose.yaml up -d --build
 
Step 3. Clean and build the nested projects/springboot applications 
 
Step 4. Replace application.yaml file contents with application-local-dev.yaml [*where applicable*]

Step 5. Go to each SpringBoot project and run main Application.

**Wait until the log message appears on screen indicating the service is started, for example (ORCHESTRATOR SERVICE STARTED)*

## Microservices REST API URLs

 - http://localhost:8083/orchestrator-service/swagger-ui/index.html
 - http://localhost:8080/order-service/swagger-ui/index.html
 - http://localhost:8082/payment-service/swagger-ui/index.html
 - http://localhost:8086/inventory-service/swagger-ui/index.html

## Creating a successful Event

When all services are up and running, go to below URL

http://localhost:8080/order-service/swagger-ui/index.html

Try creating an Order with below sample JSON request

/order/create

    {
      "uuidCustomerAccount" = "String"
      "uuidItem" = "String"
    }

From the ***Orchestrator service console output*** a success 'Order Complete' message should appear. In   ***Order service console output***, Order should be updated as well with status **CREATED**.

Process can be retrieved from Orchestrator Service REST API
 http://localhost:8083/orchestrator-service/swagger-ui/index.html

![Swager Orchestrator Service](/images/Orchestrator_Swagger_Example.png)

## Creating a Failed Event with Rollback

When all services are up and running, go to below URL

http://localhost:8080/order-service/swagger-ui/index.html

Try creating an Order with below sample JSON request
A customer with inciefficient funds to pay for the Order Item.
This will result in a failed payment debit attempt. In continuation the Order will be updated from the event, as **CANCELLED**;

Fell free to grab a copy of this sample code, and play it yourself.
-->

\documentclass[11pt]{article}
\usepackage[utf8]{inputenc}
\usepackage{hyperref}
\usepackage{graphicx}

\title{Microservices with Orchestrator Including Saga Pattern Rollback}
\author{}
\date{}

\begin{document}

\maketitle

\section*{Introduction}

This project demonstrates the orchestration of multiple microservices using an event-driven architecture and reactive programming. The application employs the \textbf{Saga Pattern} to manage distributed transactions, including rollback mechanisms in case of failure.

\section*{Requirements}

To build and run the application, ensure the following tools are installed:
\begin{itemize}
    \item \href{https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html}{JDK 17 or newer}
    \item \href{https://www.jetbrains.com/idea/}{IntelliJ IDEA or any Java-compatible IDE}
    \item \href{https://www.docker.com/products/docker-desktop}{Docker 4.36.0 or newer}
\end{itemize}

\section*{Overview}

The application consists of several microservices built with \textbf{Spring Boot 3}, communicating via \textbf{Kafka} as the event broker, with \textbf{Zookeeper} for coordination. It implements an \textit{Order Management System}, following these steps:
\begin{enumerate}
    \item An order starts in a \textbf{Pending} state.
    \item The \textbf{Saga Pattern} orchestrates a workflow involving:
    \begin{itemize}
        \item Payment processing.
        \item Inventory stock deduction.
    \end{itemize}
    \item If all steps succeed, the order transitions to \textbf{Created}.
    \item If any step fails, a rollback is triggered, and the order is marked as \textbf{Cancelled}.
\end{enumerate}

\section*{Running the Application}

You can run the microservices using one of the following methods:
\subsection*{Option 1: Full Docker Deployment}
\begin{enumerate}
    \item \textbf{Build the .jar files:}
    \begin{verbatim}
/common-orchestration/mvnw clean install
/inventory-service/mvnw clean install
/orchestrator-service/mvnw clean install
/order-service/mvnw clean install
/payment-service/mvnw clean install
    \end{verbatim}
    \item \textbf{Start the containers:}  
    From the root directory, run:
    \begin{verbatim}
docker-compose -f docker-compose.yaml up -d --build
    \end{verbatim}
\end{enumerate}

\subsection*{Option 2: Run Kafka in Docker and Spring Boot in IDE}
\begin{enumerate}
    \item \textbf{Start Kafka:}  
    From the root directory, run:
    \begin{verbatim}
docker-compose -f docker-compose.yaml up -d --build
    \end{verbatim}
    \item \textbf{Run Spring Boot services:}
    \begin{enumerate}
        \item Import the project into your IDE.
        \item Replace \texttt{application.yaml} contents with \texttt{application-local-dev.yaml}, where applicable.
        \item Clean and build all nested Spring Boot applications.
        \item Run the main application for each service.
    \end{enumerate}
\end{enumerate}

\section*{Microservices REST API URLs}

\begin{itemize}
    \item \textbf{Orchestrator Service:} \url{http://localhost:8083/orchestrator-service/swagger-ui/index.html}
    \item \textbf{Order Service:} \url{http://localhost:8080/order-service/swagger-ui/index.html}
    \item \textbf{Payment Service:} \url{http://localhost:8082/payment-service/swagger-ui/index.html}
    \item \textbf{Inventory Service:} \url{http://localhost:8086/inventory-service/swagger-ui/index.html}
\end{itemize}

\section*{Testing the Application}

\subsection*{Creating a Successful Event}
\begin{itemize}
    \item Access the \textbf{Order Service API}: \url{http://localhost:8080/order-service/swagger-ui/index.html}
    \item Create an order with a sample JSON request:
    \begin{verbatim}
{
  "uuidCustomerAccount": "String",
  "uuidItem": "String"
}
    \end{verbatim}
    \item Verify the success message in the \textbf{Orchestrator Service console output}:  
    \texttt{Order Complete}.
\end{itemize}

\subsection*{Creating a Failed Event with Rollback}
\begin{itemize}
    \item Use a customer account with insufficient funds in the payment step.
    \item Observe that the payment fails, and the order transitions to \textbf{Cancelled}.
\end{itemize}

\section*{Conclusion}

This project demonstrates the power of microservices, event-driven architecture, and the Saga Pattern in managing distributed transactions with resilience and rollback capabilities. Feel free to explore and modify the code!

\end{document}
