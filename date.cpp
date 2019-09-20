#include <string>
#include <exception>

#define Abs(x) ((x) >= 0 ? (x) : -(x)) 

class Date
{
	public:
		Date(int year, int month, int day);
		Date(int date);
		static int difference(int firstDate, int secondDate);
	
	private:
		int year;
		int month;
		int day;
		static int monthDays[13];
		static int countLeapYears(Date &date);
		bool isValidDate(int year, int month, int day);
		bool isLeapYear(int year);
};

class DateFormatException: public std::exception {	
	public:
		DateFormatException(std::string msg) : errMsg(msg) {}
		~DateFormatException() throw () {}
		const char *what() const throw() { return errMsg.c_str(); }
	
	private:
		std::string errMsg;
};

Date::Date(int year, int month, int day) {
	if (isValidDate(year, month, day)) {
		this->year = year;
		this->month = month;
		this->day = day;
	} else {
		throw DateFormatException("Invalid Date value");
	}
}

Date::Date(int date): Date(date/10000, date/100%100, date%100) {}

int Date::difference(int firstDate, int secondDate) {
	Date first = Date(firstDate);
	Date second = Date(secondDate);
	
	int diff = first.year * 365 + first.day
				+ countLeapYears(first);

	for (int i = 1; i < first.month; i++) { 
		diff += monthDays[i]; 
	} 

	diff -= second.year * 365 + second.day
				+ countLeapYears(second); 

	for (int i = 1; i < second.month; i++) { 
		diff -= monthDays[i]; 
	} 

	return Abs(diff); 
}

int Date::monthDays[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

int Date::countLeapYears(Date &date) { 
	int years = date.year; 

	if (date.month <= 2)
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
