#include "hotel_manager.h"
#include "date.h"

HotelManager::HotelManager() {

}

HotelManager::~HotelManager() {

}

Response HotelManager::signUp(Query &query) {
	Response response;
	return response;
}

Response HotelManager::logIn(Query &query) {
	Response response;
	return response;
}

Response HotelManager::handleQuery(Query &query) {
	Response response;
	return response;
}

void HotelManager::Customer::listHotel(Query &query, Response &response) {

}

void HotelManager::Customer::makeOrder(Query &query, Response &response) {

}

void HotelManager::Customer::listOrder(Query &query, Response &response) {

}

void HotelManager::Customer::cancelOrder(Query &query, Response &response) {

}

void HotelManager::Customer::modifyOrder(Query &query, Response &response) {

}

void HotelManager::Service::doService(Customer &customer, Query &query, Response &response) {
	switch (query.type) {
		case BOOK:
			customer.makeOrder(query, response);
			break;
		case CANCEL:
			customer.cancelOrder(query, response);
			break;
		case MODIFY:
			customer.modifyOrder(query, response);
			break;
		case LISTORDER:
			customer.listOrder(query, response);
			break;
		case LISTHOTEL:
			customer.listHotel(query, response);
			break;
		default:
			throw new CustomerActionFailException("Unknown query");
	}
}
