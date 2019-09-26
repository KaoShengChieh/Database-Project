#include "facade.h"

extern char socketStream[STREAMSIZE];

Query Serializer::deserializeQuery(char socketStream[]) {
	Query query;
	char userName[USERNAMESIZE];
	char bookID[BOOKIDSIZE];
	
	sscanf(socketStream, "%d%s%s%d%d%d%d%d%d%d",
		&query.type, userName, bookID, &query.hotelID,
		&query.roomNum[0], &query.roomNum[1], &query.roomNum[2],
		&query.checkin, &query.checkout, &query.price);
		
	query.userName = *(new std::string(userName));
	query.bookID = *(new std::string(bookID));
	
	return query;
}

char *serializeResponse(Response &response) {
	Query result = Query(response._result);
	
	sprintf(socketStream, "%d %d %s %d %s %s %d %d %d %d %d %d %d", response.isSuccess, (int)response._errMsg.length(), response._errMsg.c_str(), result.type, result.userName.c_str(), result.bookID.c_str(), result.hotelID,
		result.roomNum[0], result.roomNum[1], result.roomNum[2],
		result.checkin, result.checkout, result.price);
		
	//TODO orderList & hotelinfoList
		
	return socketStream;
}
