Hotel_Manager: hotel_manager.o facade.o serializer.o date.o
	# donothing for now
	
hotel_manager.o: hotel_manager.cpp
	g++ -c hotel_manager.cpp -Wall

facade.o: facade.cpp
	g++ -c facade.cpp -Wall

serializer.o: serializer.cpp
	g++ -c serializer.cpp -Wall
	
date.o: date.cpp
	g++ -c date.cpp -Wall

clean:
	rm -f hotel_manager.o facade.o serializer.o date.o
