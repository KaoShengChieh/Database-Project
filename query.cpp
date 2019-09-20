#include <cstdio>
#include <string>
#include "HotelManager.h"

class Query
{
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
		static Query deserialize(char socketStream[]);
};

bool Query::quit() { return type == QUIT; }

Query Query::deserialize(char socketStream[]) {
	Query query;
	char userName[USERNAMESIZE];
	char bookID[BOOKIDSIZE];
	
	sprintf(socketStream, "%d%s%s%d%d%d%d%d%d%d",
		query.type, userName, bookID, query.hotelID,
		query.roomNum[0], query.roomNum[1], query.roomNum[2],
		query.checkin, query.checkout, query.price);
		
	query.userName = *(new std::string(userName));
	query.bookID = *(new std::string(bookID));
	
	return query;
}
