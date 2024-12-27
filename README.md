# Bookstore Backend System and Recommendation System

This project includes two main components:
1. **Bookstore Backend System**: Built with Spring Boot to manage core bookstore functionalities.
2. **Book Recommendation System**: A simple convolutional neural network model that uses user view time as a feature to recommend books.
   I chose the PyTorch to create the recommendation system because it is simple and free.As an open-source deep learning framework 
   it is easy to use when the system is beginning.We can choose more complex framework to build this recommendation system as the system grows. 

---

## **Backend System**

### **Overview**

The backend system is developed with Spring Boot and provides the following functionalities:
- **Book Management**: Add, query, update, and delete books.
- **Inventory Management**: Update stock and check stock availability.
- **User Management**: Register, login, and view user information.
- **Order Processing**: Create, cancel, and query order status.

To run this program, we need to config mysql and redis info yml
Then we can use java - jar 
or make docker image to start this program

## **Recommand System**

Firstly,we should install the requirement
pip install -r requirements.txt
and run python train.py to train the model
Then,run python -m recommand.api.api to open the recommand api

