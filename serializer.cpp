#include "HotelManager.h"

Query Serializer::deserializeQuery(char socketStream[]) {
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
