<center>
  <table>
    <tr>
      <td>Outline</td>
    </tr>
    <tr>
      <td align="left">
        A. Project Description<br>
        B. Functional Requirements<br>
        C. Database Design with ER-Diagram – Third-Party Payment Platform<br>
        D. Database Schema – Distributed Database System<br>
        E. System Architecture – Numerous Clients Online<br>
        F. Implement Philosophy – Design Pattern and Extension<br>
        G. Package Hierarchy<br>
        H. Platform/Tool/Protocol
      </td>
    </tr>
  </table>
</center>

## Project Description
1. This system is an online hotel booking system composed of "numerous client applications" and "single management system server". This would be introduced later in “G. System Architecture – Numerous Clients Online”.
2. Our online hotel booking system is constructed as a third-party payment platform. This would be introduced later in “E. Database Design with ER-Diagram - Third-party Payment Platform”.
3. Our third-party payment platform is built as distributed database systems. This would be introduced later in “F. Database Schema – Distributed Database System”.

## Package Hierarchy
The client only needs to own the files in the Client folder, and the system administrator only needs to own the files in the Server folder. After the booking system executes runServer.sh, several clients can be connected to execute runClient.sh.

## Functional Requirements

<img src="https://i.imgur.com/hnFoOcV.jpg" width="500">

### Sign up
Users can sign up to be a member of the platform.
1. Input：userName, password, realName, email, membership
2. Output：none
3. Data processing：Add a new account to Hotel Manager DB
4. System Error handling：ManagerException

<img src="https://i.imgur.com/2lcYp32.jpg" width="500">

### Login
Members can login to the platform by the unique user name.
1. Input：userName, password
2. Output：none
3. Data processing：If successful, enter into online state
4. System Error handling：ManagerException

<img src="https://i.imgur.com/bPUZIbI.jpg" width="500">

### List hotel
Member can search hotels for the specific city, room type, expected check-in and Check-out dates, and hotel star.
1. Input：city, starCondition, roomNum(array), checkIn, checkOut
2. Output：List of Hotels’ information with Hotel_ID, Star, City, Address, Price 
3. Data processing：enter the hotel DB to query the hoel meeting the condition
4. System Error handling：HotelException

<img src="https://i.imgur.com/FRmDzCN.png" width="500">

<img src="https://i.imgur.com/3EEObYv.png" width="500">

<img src="https://i.imgur.com/u7PEpD7.png" width="500">

### Book
Member can make reservation to order the hotel room.
1. Input：memerID, hotelID, roomNum(array), checkIn, checkOut
2. Output：order Information with book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, expirationDate
3. Data processing：enter the Hotel Manager DB and Hotel DB to insert new reservation respectively
4. System Error handling：ManagerException, HotelException

<img src="https://i.imgur.com/Bqe0bEd.png" width="500">

<img src="https://i.imgur.com/29vt2dv.png" width="500">

### Modify
VIP member can modify his own previous reservation before paying the fees.
1. Input：bookID, roomNum(array), checkIn, checkOut
2. Output：order Information with book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, expirationDate
3. Data processing：enter the Hotel Manager DB and Hotel DB to update the reservation respectively
4. System Error handling：ManagerException, HotelException

<img src="https://i.imgur.com/JQYejSD.png" width="500">

### Cancel
Member can cancel the reservation before paying the fees.
1. Input：bookID
2. Output：void
3. Data processing：enter the Hotel Manager DB and Hotel DB to delete the reservation respectively
4. System Error handling：ManagerException, HotelException

<img src="https://i.imgur.com/1CPmAYW.png" width="500">

### List reservation
Member can view unpaid reservations they have made before expiration.
1. Input：memberID
2. Output：List of order Information with book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, expirationDate
3. Data processing：enter the Hotel Manager DB to query the unexpired reservation made by specific member and then enter the Hotel DB to query the related hotel information like star, city and address
4. System Error handling：ManagerException, HotelException

<img src="https://i.imgur.com/EJZTEja.png" width="500">

### List order
Member can view orders they have paid.
1. Input：memberID
2. Output：List of order Information with book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, bank_name
3. Data processing：enter the Hotel Manager DB to query the order history made by specific member and then enter the Hotel DB to query the related hotel information like star, city and address
4. System Error handling：ManagerException, HotelException

<img src="https://i.imgur.com/2LNOy53.jpg" width="500">

### Pay
Member can pay for the reservation with credit card via submitting the fees to the cash system.
1. Input：bookID, bankID, creditcardID, expirationDate, securityCode
2. Output：order Information book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, expirationDate
3. Data processing：enter Hotel Manager DB to insert fees, enter Cash System to check account and credit card. If successful, update natural account balance. Then go back to Hotel Manager DB to move reservation and fees into order history and notify the hotel.
There are several possible results：
   * During payment, waiting for verification by the bank
   * Payment success, send e-mail with transaction information
   * Payment fail, the card number doesn’t exist or card information is incorrect
   * Payment fail, the card does not belong to banks cooperating with this system
4. System Error handling：ManagerException, CashSystemException, HotelExcep-tion

![](https://i.imgur.com/ewfZnxE.png)

### Play Ad
Hotel Manager reponses ads to client and plays Ads on the platform.
1. Input：client_ID
2. Output：Ad_id
3. Data processing：insert the advertisement to the response to client.
4. System Error handling：ManagerException

<img src="https://i.imgur.com/9KKwVjt.jpg" width="500">

## Database Design with ER-Diagram – Third-party Payment Platform

Our online hotel booking system is constructed as a third-party payment platform.

Through this platform, a member can make a payment for the fees corresponding to a hotel room reservation. The third-party provider, which is our platform, then calls the authorized cash system to confirm whether it receives the payment from the member, verifies that the funds are available, and debits the member’s account. If the payment is confirmed, our platform then calls the authorized cash system again to forwarded the money to the hotel account, which may be on the different portal.

The hotel account may be credited in minutes or days, but the funds may be withdrawn to a bank account or used to conduct other transactions once the deposit has been made in the account.

![](https://i.imgur.com/2nbxoUP.png)

The figure shown above is the ER-diagram of our distributed database systems, which will be introduced later in “F. Database Schema – Distributed Database System”. Knowing that our ER-diagram is somewhat intricate, we give an abstract as follows. Suppose the entity “hotel manager” is the origin, the diagram can be roughly divided into four parts, which are:
1. First quadrant: Hotel System
It is composed of thousands of hotels and millions of room reservations, which are primary merchandise in our system.
2. Second quadrant and periphery: Cashing System
It involves monetary system and payment flow (or flow of money in plain words). The monetary system has been elaborated in the second part of our preamble, so we merely describe the payment flow beneath it here. The payment flow consists of the payer (e.g. member in the trade transaction) and the payer's bank plus the payee (e.g. hotel) and the payee's bank. The banking system and its interbank payment transfer systems provide the primary infrastructure for fund transfers between customers. But hotel manager grips the sluices of payment flow.
3. Third quadrant: Client Application
This would be introduced later in “G. System Architecture – Numerous Clients Online”.
4. Fourth quadrant: Advertisement System
Application would have some pop-up advertisements if the visitor is not qualified as a VIP. Advertiser can send their commercial to hotel manager. The latter will charge the former regarding the content.

Since the attributes in our system are too many to present in the ER diagram, we only show them in “C. Database Requirements” and “F. Database Schema – Distributed Database System”.

## Database Schema – Distributed Database System

To make it more comprehensible, consider one transaction example:

A member uses our payment service with a credit card of certain bank. Our platform requests the bank to start going over the verification. Then our platform forwards the fund and order information to the hotel. The hotel offers reservation.

![](https://i.imgur.com/eHrxQV5.png)
![](https://i.imgur.com/oN1QoQU.png)
![](https://i.imgur.com/oYedxDG.png)

## System Architecture – Numerous Clients Online

This system allows a guest (non-member visitor) find eligible hotels with customized enquiry. A registered member can furthermore exploit it to book the hotel they want, based on the results of the previous inquiry. The system also allows a member reach the details of his/her order, then provide advanced function regarding his/her membership (for example, general member can cancel the order, and VIP can modify the order). In addition, the system also provides various features such as sorting. Users can sort the results according to their needs. For example, they can sort the result of available hotels by either star, price or location. Most importantly, GUI (graph user interface) can maintain highly interactive even when hundreds of people are enjoying our application simultaneously.

<img src="https://i.imgur.com/Qw1LxK4.png" width="600">

## Platform/Tool/Protocol
* **Developing language**: Java SE 11.0.5
* **Graph User Interface**: Java Swing, Java AWT. Eclipse, Window Builder
* **Data encryption**: MD5
* **Json parser**: json-simple
* **DBMS**: SQLite
* **Database Connectivity**: JDBC
* **E-mail Service**: SMTP and SSL suthorization
