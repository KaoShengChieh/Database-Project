#include "HotelManager.h"

void Response::setErrorMessage(std::string errorMessage) {
	isSuccess = false;
	_errorMessage = errorMessage;
}

std::string Response::getErrorMessage() {
	return _errorMessage;
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
	_orderList = orderList;
}

void Response::setHotelInfoList(std::vector<HotelInfo> &hotelInfoList) {
	isSuccess = true;
	_hotelInfoList = hotelInfoList;
}
