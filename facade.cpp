#include "facade.h"

bool Query::quit() {
	return type == QUIT;
}

HotelInfo::HotelInfo(int hotelID, int star, std::string city, std::string address, int price):
_hotelID(hotelID), _star(star), _city(city), _address(address), _price(price) {}

void Response::setErrMsg(std::string errMsg) {
	isSuccess = false;
	_errMsg = errMsg;
}

std::string Response::getErrMsg() {
	return _errMsg;
}

void Response::setResult(Query &result) {
	isSuccess = true;
	_result = *(new Query(result));
}

Query Response::getResult() {
	return *(new Query(_result));
}

void Response::setOrderList(std::vector<Query> &orderList) {
	isSuccess = true;
	_orderList = orderList; //TODO need to revise
}

void Response::setHotelInfoList(std::vector<HotelInfo> &hotelInfoList) {
	isSuccess = true;
	_hotelInfoList = hotelInfoList; //TODO need to revise
}
