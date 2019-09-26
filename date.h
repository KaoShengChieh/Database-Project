#ifndef DATE_H
#define DATE_H

#include <string>
#include <exception>

class Date {
	public:
		Date(int year, int month, int day);
		Date(int date);
		static int difference(int firstDate, int secondDate);
	
	private:
		int _year;
		int _month;
		int _day;
		static int monthDays[13];
		static int countLeapYears(Date &date);
		bool isValidDate(int year, int month, int day);
		bool isLeapYear(int year);
};

class DateFormatException: public std::exception {	
	public:
		DateFormatException(std::string msg);
		~DateFormatException() throw();
		const char *what() const throw();
	
	private:
		std::string errMsg;
};

#endif
