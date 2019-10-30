TARGET = Hotel_Manager HotelDataParser.class
OBJECT = hotel_manager.o visitor.o facade.o serializer.o date.o md5.o
CC = g++ -Wall
JCC = javac -cp
LIB = ./lib/
SQLITE_DRIVER = sqlite-jdbc-3.23.1.jar
JSON_PARSER = json-simple-1.1.1.jar
MATH = -lm

All: $(TARGET)

Hotel_Manager: $(OBJECT)
	# do nothing for now
	
hotel_manager.o: hotel_manager.cpp
	$(CC) -c hotel_manager.cpp

visitor.o: visitor.cpp
	$(CC) -c visitor.cpp

facade.o: facade.cpp
	$(CC) -c facade.cpp

serializer.o: serializer.cpp
	$(CC) -c serializer.cpp
	
date.o: date.cpp
	$(CC) -c date.cpp

md5.o: md5.cpp
	$(CC) -c md5.cpp $(MATH)
	
HotelDataParser.class:
	$(JCC) .:$(LIB)$(SQLITE_DRIVER):$(LIB)$(JSON_PARSER) HotelDataParser.java

clean:
	rm -f $(OBJECT)
	
