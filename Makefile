Hotel_Manager: facade.o serializer.o date.o
	# donothing for now
	
facade.o: facade.cpp
	g++ -c facade.cpp -Wall

serializer.o: serializer.cpp
	g++ -c serializer.cpp -Wall
	
date.o: date.cpp
	g++ -c date.cpp -Wall

clean:
	rm -f facade.o serializer.o date.o
