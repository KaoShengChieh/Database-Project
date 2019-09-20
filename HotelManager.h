#ifndef HOTELMANAGER_H
#define HOTELMANAGER_H

#include <string>
#include <vector>

#define STREAMSIZE ??
#define USERNAMESIZE 32
#define BOOKIDSIZE 10

enum QueryType {LOGIN, SIGNUP, BOOK, CANCEL, MODIFY, LISTORDER, LISTHOTEL, QUIT};

class Query {
	public:
		QueryType type;
		std::string userName;
		std::string bookID;
		int hotelID;
		int roomNum[3];
		int checkin;
		int checkout;
		int price;
		bool quit();
};

class HotelInfo {
	public:
		int _hotelID;
		int _star;
		std::string _city;
		std::string _address;
		int _price;
		HotelInfo(int hotelID, int star, std::string city, std::string address, int price);
};

class Response {
	public:
		bool isSuccess;
		std::string _errorMessage;
		Query _result;
		std::vector<Query> _orderList;
		std::vector<HotelInfo> _hotelInfoList;
		void setErrorMessage(std::string errorMessage);
		std::string getErrorMessage();
		void setResult(Query &result);
		Query getResult();
		void setOrderList(std::vector<Query> &orderList);
		void setHotelInfoList(std::vector<HotelInfo> &hotelInfoList);
};

class Serializer {
	public:
		Query deserializeQuery(char socketStream[]);
		char *serializeResponse(Response &response);
};

#endif
