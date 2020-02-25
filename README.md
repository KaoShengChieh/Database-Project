Online Hotel Booking System
===

<center>
  <table>
    <tr>
      <td>Outline</td>
    </tr>
    <tr>
      <td align="left">
        A. Member<br>
        B. Project Description<br>
        C. Database Requirements<br>
        D. Functional Requirements<br>
        E. Database Design with ER-Diagram - Third-Party Payment Platform<br>
        F. Database Schema – Distributed Database System<br>
        G. System Architecture – Numerous Clients Online<br>
        H. Implement Philosophy – Design Pattern and Extension<br>
        I. Package Hierarchy<br>
        J. Platform/Tool/Protocol
      </td>
    </tr>
  </table>
</center>


| Outline                                                                                                                                                                                                                                                                                                                                                          |
|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|A. Member<br>B. Project Description<br>C. Database Requirements<br>D. Functional Requirements<br>E. Database Design with ER-Diagram - Third-Party Payment Platform<br>F. Database Schema – Distributed Database System<br>G. System Architecture – Numerous Clients Online<br>H. Implement Philosophy – Design Pattern and Extension<br>I. Package Hierarchy<br>J. Platform/Tool/Protocol |



## Project Description
1. This system is an online hotel booking system composed of "numerous client applications" and "single management system server". This would be introduced later in “G. System Architecture – Numerous Clients Online”.
2. Our online hotel booking system is constructed as a third-party payment platform. This would be introduced later in “E. Database Design with ER-Diagram - Third-party Payment Platform”.
3. Our third-party payment platform is built as distributed database systems. This would be introduced later in “F. Database Schema – Distributed Database System”.

## Functional Requirements

### Sign up
Users can sign up to be a member of the platform.
1. Input：userName, password, realName, email, membership
2. Output：none
3. Data processing：Add a new account to Hotel Manager DB
4. System Error handling：ManagerException

### Login
Members can login to the platform by the unique user name.
1. Input：userName, password
2. Output：none
3. Data processing：If successful, enter into online state
4. System Error handling：ManagerException

### List hotel
Member can search hotels for the specific city, room type, expected check-in and Check-out dates, and hotel star.
1. Input：city, starCondition, roomNum(array), checkIn, checkOut
2. Output：List of Hotels’ information with Hotel_ID, Star, City, Address, Price 
3. Data processing：enter the hotel DB to query the hoel meeting the condition
4. System Error handling：HotelException

### Book
Member can make reservation to order the hotel room.
1. Input：memerID, hotelID, roomNum(array), checkIn, checkOut
2. Output：order Information with book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, expirationDate
3. Data processing：enter the Hotel Manager DB and Hotel DB to insert new reservation respectively
4. System Error handling：ManagerException, HotelException

### Modify
VIP member can modify his own previous reservation before paying the fees.
1. Input：bookID, roomNum(array), checkIn, checkOut
2. Output：order Information with book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, expirationDate
3. Data processing：enter the Hotel Manager DB and Hotel DB to update the reservation respectively
4. System Error handling：ManagerException, HotelException

### Cancel
Member can cancel the reservation before paying the fees.
1. Input：bookID
2. Output：void
3. Data processing：enter the Hotel Manager DB and Hotel DB to delete the reservation respectively
4. System Error handling：ManagerException, HotelException

### List reservation
Member can view unpaid reservations they have made before expiration.
1. Input：memberID
2. Output：List of order Information with book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, expirationDate
3. Data processing：enter the Hotel Manager DB to query the unexpired reservation made by specific member and then enter the Hotel DB to query the related hotel information like star, city and address
4. System Error handling：ManagerException, HotelException

### List order
Member can view orders they have paid.
1. Input：memberID
2. Output：List of order Information with book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, bank_name
3. Data processing：enter the Hotel Manager DB to query the order history made by specific member and then enter the Hotel DB to query the related hotel information like star, city and address
4. System Error handling：ManagerException, HotelException

### Pay
Member can pay for the reservation with credit card via submitting the fees to the cash system.
1. Input：bookID, bankID, creditcardID, expirationDate, securityCode
2. Output：order Information book_ID, hotel_ID, star, city, address, roomNum, checkin, checkout, price, expirationDate
3. Data processing：enter Hotel Manager DB to insert fees, enter Cash System to check account and credit card. If successful, update natural account balance. Then go back to Hotel Manager DB to move reservation and fees into order history and notify the hotel.
There are several possible results：
   * During payment, waiting for verification by the bank
   * Payment success, send e-mail with transaction information
   * Payment fail, the card number doesn’t exist or card information is incorrect
   * Payment fail, the card does not belong to banks cooperating with this system
4. System Error handling：ManagerException, CashSystemException, HotelExcep-tion

### Play Ad
Hotel Manager reponses ads to client and plays Ads on the platform.
1. Input：client_ID
2. Output：Ad_id
3. Data processing：insert the advertisement to the response to client.
4. System Error handling：ManagerException

## Database Design with ER-Diagram - Third-party Payment Platform

Our online hotel booking system is constructed as a third-party payment platform.

Through this platform, a member can make a payment for the fees corresponding to a hotel room reservation. The third-party provider, which is our platform, then calls the authorized cash system to confirm whether it receives the payment from the member, verifies that the funds are available, and debits the member’s account. If the payment is confirmed, our platform then calls the authorized cash system again to forwarded the money to the hotel account, which may be on the different portal.

The hotel account may be credited in minutes or days, but the funds may be withdrawn to a bank account or used to conduct other transactions once the deposit has been made in the account.

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

## System Architecture – Numerous Clients Online

This system allows a guest (non-member visitor) find eligible hotels with customized enquiry. A registered member can furthermore exploit it to book the hotel they want, based on the results of the previous inquiry. The system also allows a member reach the details of his/her order, then provide advanced function regarding his/her membership (for example, general member can cancel the order, and VIP can modify the order). In addition, the system also provides various features such as sorting. Users can sort the results according to their needs. For example, they can sort the result of available hotels by either star, price or location. Most importantly, GUI (graph user interface) can maintain highly interactive even when hundreds of people are enjoying our application simultaneously.

## Implement Philosophy – Design Pattern and Extension
<table>
  <tr>
    <td>功能：多人連線系統、搶票</td>
    <td>設計：Guarded Suspension</td>
  </tr>
  <tr>
    <td colspan="2">情境：多人連線系統的Server必須同時處理來自多個客戶端的Query，為了迅速接受客戶<br>的Query，它要維持一個Query queue，客戶的Query會先儲存至Query queue中，<br>Server會從緩衝區中取出Query並執行，如Query queue中沒有Query，Server就等待，<br>直到被通知有新的Query存入Query queue中。Server socket thread和Client socket <br>thread，所以必須對Query queue進行防護，當Query queue中沒有Query時，服務元件<br>必須等待直到被通知有新的Query。強調的是對共用資料的防護，避免共用存取的競爭問<br>題。</td>
  </tr>
</table>
功能：多人連線系統、搶票
設計：Guarded Suspension
情境：多人連線系統的Server必須同時處理來自多個客戶端的Query，為了迅速接受客戶的Query，它要維持一個Query queue，客戶的Query會先儲存至Query queue中，Server會從緩衝區中取出Query並執行，如Query queue中沒有Query，Server就等待，直到被通知有新的Query存入Query queue中。Server socket thread和Client socket thread，所以必須對Query queue進行防護，當Query queue中沒有Query時，服務元件必須等待直到被通知有新的Query。強調的是對共用資料的防護，避免共用存取的競爭問題。

功能：Interactive服務
設計：Thread-Per-Message
情境：Server端對每個連線的Client端使用一個Thread來處理，Server端接受連線後，使用一個Thread來處理該次連線，可馬上傾聽下一個Client端連線。並且，Server從任一個Client端接收到資料之後，可以馬上切換到該Thread來處理，並透過下一個Visitor設計提到的accept()方法很快地返回，因此Server對於客戶端可以有很高的回應性。






功能：根據身分提供服務
設計：Visitor
情境：如果系統中有訪客、會員與VIP，假設經過設計考量，讓訪客只能查詢，會員可以進一步訂房和取消訂單，VIP可以再有修改訂單和退款的特殊服務。
功能：接收和回應網路封包
設計：Proxy
情境：在Client端送出Query或是Server端送回Response前，建立的是queryProxy或responseProxy實例，真正需要處理服務時，才會真正建立Instance並馬上交給網路服務，使用這種方式，即使用戶操作很多，也不會拖累發送請求的速度。

功能：部分耗時工作交給客戶端
設計：Command
情境：當客戶要求符合條件的旅館清單時，由於處理旅館搜尋和排序非常耗時，除了用前面使用到的Thread-Per-Message處理各個客戶的需求保持高回應性之外，還將旅館清單交由客戶端進行排序，但由於程式並部會預先知道客戶會使用什麼搜尋條件，所以先將排序方法抽象化出來，實際要排序的時候，才根據條件時做排序方法。

功能：穩定的訂房系統Server
設計：Two-phase Termination
情境：Server會有許多Thread正在週期性的運作，在「運作階段」Client送出了停止連線的Query(例如關閉GUI)，這時候Thread不會慌張的馬上終止目前的工作，而是先完成這一次週期的工作，然後進入「善後階段」處理Serialized的物件和網路串流，即中止「運作階段」，並完成「善後階段」，完整的完成Thread的工作。保持Server可以在對某個Client的連續或服務無預警的crash，不會影響整體運作，可以繼續接受其他Client的連線和提供服務。



功能：分離Server的服務和計算
設計：Strategy
情境：訂房網站實際連線之後Manager所提供的服務，Server並不用知曉。將服務與實作封裝為一個個的Strategy物件，讓使用服務的客戶端可以依需求抽換服務或演算法，而不用關心服務使用的演算法或實作方式。

功能：客戶端低運算成本待命
設計：Producer Consumer
情境：Server對於每個連線的Client，都會建立一個Thread物件封裝客戶端的相關資訊，並且加入一個叫current user的Linked List中集中管理。User在Server所擁有的Thread可隨時加入或退出current user的Linked List，當Linked List狀態發生變化時，它必須通知所有的Thread ， Thread會檢視Linked List內的狀態變化，作出對應的動作，或待會再回來處理。

功能：訂閱其他Client的行為
設計：Observer
情境：我們強化上一頁提到的Producer Consumer模式，將current user的Linked List視為Observer模式中的主題（subject）與針對每個連到Server的Client建立觀察者（observer），主題會被多個觀察者訂閱，當主題的狀態發生變化時，它必須通知（notify）所有訂閱它的觀察者，觀察者會檢視主題的狀態變化，並作出對應的動作，而本設計實際上是為了下一個設計鋪路。

功能：多機操作、即時更新
設計：Model-View-Controller
情境：我們的系統除了支援多人在線進行操作，還進一步讓一名使用者可以使用多台裝置登入，再次提升了搶票的多元性及強度。我們使用MVC model 解決帳號資訊的同步問題。假如兩台裝置A、B登入同一帳號，使用裝置A訂房後，裝置B的Reservation list要顯示裝置A加入的那筆訂單；同樣的，使用裝置A繳款後，裝置B無須點選重新整理，Order list就要立即加入已經繳款完成的訂單。而這個model的完成是得力於前兩個設計的幫助。

功能：保證同步時的資料一致性
設計：Concurrency Control
情境：如果有一個房間有可能同時間會有許多Client的服務想要對它進行讀取與寫入，如果在讀取的時候，有寫入者想要對資料進行寫入，則必須等待讀取者讀取完畢，相反的如果在寫入時有客戶想要讀取資料，則必須等待，以確保讀出來的資料是最新的資料。另外，若讓Service在讀取或寫入Room時對Reservation進行鎖定，我們還可以進一步只鎖定Room的某個時間段，而不是將整個Room的讀寫都進行鎖定。不過很可惜，這個部分直到我們撰寫本報告的當下都尚未成功。

功能：分離Server端和Client端實作
設計：Façade
情境：在網路的Server端和Client端分別釐出一個入口（Façade）介面，讓對程式庫的依賴實現在對介面的實作上。GUI不需要再知曉HotelManager各種API的存在，因而不會對程式庫產生耦合，從另一個角度來想，Server由的訂房系統的開發人員所撰寫，提供給另一個撰寫GUI的開發人員所使用，則後者並不用一定得知曉訂房系統如何使用，有利於分工合作，將來就算開發訂房系統的開發人員改寫或重新實作了另一個Service，撰寫Application的開發人員也無需修改寫它的程式。

功能：版本控制
設計：Reuse
情境：如上一個Façade設計所敘述，我們透過事前分析，述明要傳送的資料內容後，再將Server端和Client端的接口建立好，如此一來，Server端和Client端就可以分開開發了。在Jacobson的六個reuse階段中，我們在Black-box reuse的階段，因為我們開發人員彼此之間可以互相信任，進而重複利用彼此的程式碼以節省開發時間，而且因為我們事前有針對整個架構分析並設計，並要求所有開發人員都必須按照已訂好的API進行開發，以確保雙方對程式碼的語義有一致的理解，來降低修改時的維護成本。

功能：敏感資料加密
擴充：Cryptographic Hash Function
情境：考慮以下兩點
1.	網路封包可能被攔截
2.	訂房網站系統可能有內鬼
我們針對訂房系統中的使用者密碼進行加密，即使訊息被攔截，也客戶的機密資訊流出，並且在訂房系統內部也做特殊處理，即使是操作訂房網站系統的內部人員，甚至任何可以看到private變數的開發人員，也無法看到真實的內容。
我們使用的加密方式為MD5，這種密碼雜湊函式有四個主要的特性：(1) 對於任何一個給定的訊息，它都很容易就能運算出雜湊數值；(2) 難以由一個已知的雜湊數值，去推算出原始的訊息；(3) 在不更動雜湊數值的前提下，修改訊息內容是不可行的；(4) 對於兩個不同的訊息，不會有相同的雜湊數值。在資訊安全中，有許多重要的應用，都使用了密碼雜湊函式來實作，如數位簽章，訊息鑑別碼等等。

功能：錯誤訊息、廣告
擴充：Dialogue
情境：除非此Dialogue已關閉，否則Dialogue跳出時無法點擊到後方畫面。如果使用者在錯誤訊息跳出時，不慎點擊後方畫面，系統所給予的資訊可能就此忽略，抑或是廣告跳出時，使用者可藉由此方式跳開廣告的視窗，進而略過廣告，因此，我們將跳出的視窗使用Dialogue呈現，來限制使用者的操作行為。

功能：刷卡通知
擴充：Notification
情境：此系統額外添加使用SMTP寄送電子郵件通知的功能，並使用SSL驗證，顧客使用信用卡刷卡下單後，系統會以電子郵件通知持卡人付款資訊，目前僅支援Gmail和NTU Mail。

For those who are not familiar with design pattern, please refer to:
https://en.wikipedia.org/wiki/Software_design_pattern

    I. Package Hierarchy
The client only needs to own the files in the Client folder, and the system administrator only needs to own the files in the Server folder. After the booking system executes runServer.sh, several clients can be connected to execute runClient.sh.

Client
Server
Vendor
Package
App_Ubuntu
├── Hotel Booking App.jar*
└── ClientConfig.txt

App_Windows
├── Hotel Booking App.jar*
└── ClientConfig.txt

* Double click jar file to execute
Management_System
├── data
│   └── hotel.json
├── ManagementSystem.jar**
└── ServerConfig.txt


** Return "java -jar Management System.jar" in terminal to execute
Developer
Package
Client
├── images
│   └── images for GUI
/*Launch*/
├── runClient.sh
├── ClientConfig.txt
├── Client.java
/*Internet and IPC API*/
├── Packet.java
├── QueryType.java
├── Query.java
├── UserInfo.java
├── OrderInfo.java
├── CreditCardInfo.java
├── Response.java
├── HotelInfo.java
/*GUI Controller*/
├── GUIThread.java
/*GUI Module*/
├── CancelReservation.java
├── CheckReservation.java
├── CreateAccount.java
├── ListHistory.java
├── Menu.java
├── ModifyReservation.java
├── OrderHotel.java
├── PayReservation.java
├── SearchHotel.java
├── ShoppingCart.java
├── ShowSearchHotel.java
├── SignIn.java
├── Welcome.java
/*GUI Popup*/
├── AD.java
├── DatePopup.java
└── ResultPopUp.java

Server
├── data
│   /*Sample data*/
│   └── hotel.json
├── lib
│   /*Prerequisite library*/
│   ├── json-simple-1.1.1.jar
│   ├── javax.mail.jar
│   ├── activiation-1.1.1.jar
│   └── mysql-connector-
│                java-8.0.18.jar
/*Launch*/
├── runServer.sh
├── ServerConfig.txt
├── Server.java
/* Internet API*/
├── QueryType.java
├── Query.java
├── UserInfo.java
├── OrderInfo.java
├── CreditCardInfo.java
├── Response.java
├── HotelInfo.java
/*DBMS Controller*/
├── HotelManager.java
├── BasicDBPool.java
├── DBGate.java
├── DBSource.java
/* DBMS – Manager*/
├── Manager.java
├── ManagerA.java
├── ManagerDB.java
├── ManagerDBA.java
├── ManagerException.java
/* DBMS – Cash System*/
├── CashSystemDB.java
├── CashSystemDBA.java
├── CashSystemException.java
/* DBMS – Hotel*/
├── HotelDB.java
├── HotelDBA.java
├── HotelDBori.java
├── HotelDate.java
├── HotelException.java
├── ReservationInfo.java
└── RoomInfo.java



## Platform/Tool/Protocol
1. 主要開發語言使用Java SE 11.0.5
2. 前端Graph User Interface使用Java Swing、Java AWT、Eclipse、Window Builder
4. 資料序列化使用Java Object Serialization
6. 資料加密使用MD5
7. 爬蟲資料解析使用json-simple
8. 資料庫管理系統使用SQLite
9. 資料庫管理系統driver使用JDBC
10. 電子郵件服務使用SMTP和SSL驗證
