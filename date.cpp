#include "date.h"

#define Abs(x) ((x) >= 0 ? (x) : -(x)) 

DateFormatException::DateFormatException(std::string msg): errMsg(msg) {}

DateFormatException::~DateFormatException() throw () {}

const char *DateFormatException::what() const throw() {
	return errMsg.c_str();
}

Date::Date(int year, int month, int day): _year(year), _month(month), _day(day) {
	if (!isValidDate(year, month, day))
		throw DateFormatException("Invalid Date value");
}

Date::Date(int date): Date(date/10000, date/100%100, date%100) {}

int Date::difference(int firstDate, int secondDate) {
	Date first = Date(firstDate);
	Date second = Date(secondDate);
	
	int diff = first._year * 365 + first._day
				+ countLeapYears(first);

	for (int i = 1; i < first._month; i++) { 
		diff += monthDays[i]; 
	} 

	diff -= second._year * 365 + second._day
				+ countLeapYears(second); 

	for (int i = 1; i < second._month; i++) { 
		diff -= monthDays[i]; 
	} 

	return Abs(diff); 
}

int Date::monthDays[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

int Date::countLeapYears(Date &date) { 
	int years = date._year; 

	if (date._month <= 2)
		years--; 

	return years / 4 - years / 100 + years / 400; 
}

bool Date::isValidDate(int year, int month, int day) {
	if (year < 1000 || year > 9999 || month < 0 || month > 12 || day < 0)
		return false;

	if (month == 2)
		return day <= monthDays[2] + (isLeapYear(year) ? 1 : 0);
	
	return day <= monthDays[month];
}

bool Date::isLeapYear(int year) {
	return  (year % 400 == 0)
		|| ((year % 4 == 0) && (year % 100 != 0));
}
