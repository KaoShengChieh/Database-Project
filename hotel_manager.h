#ifndef HOTEL_MANAGER_H
#define HOTEL_MANAGER_H

#include <pthread.h>
#include "database.h"
#include "facade.h"

pthread_mutex_t encypt_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t book_mutex = PTHREAD_MUTEX_INITIALIZER;

class HotelManager
{
	public:
		HotelManager();
		~HotelManager();
		Response signUp(Query &query);
		Response logIn(Query &query);
		Response handleQuery(Query &query);
		
	private:
		Database database;
		void connectDBs();
		class Customer;
		class Member;
		class VIP;
		
		class Visitor {
			public:
				virtual void visit(Member *member);
				virtual void visit(VIP *vip);
		};
		
		class Visitable {
			public:
				virtual void accept(Visitor *visitor);
		};
		
		class Customer: protected Visitable {
			protected:
				bool isMember;
				bool isVIP;
			public:
				void listHotel(Query &query, Response &response);
				void makeOrder(Query &query, Response &response);
				void listOrder(Query &query, Response &response);
				void cancelOrder(Query &query, Response &response);
				void modifyOrder(Query &query, Response &response);
				virtual void accept(Visitor &visitor);
		};
		
		class CustomerActionFailException: public std::exception {	
			public:
				CustomerActionFailException(std::string msg);
				~CustomerActionFailException() throw();
				const char *what() const throw();
			
			private:
				std::string errMsg;
		};

		class Member: public Customer {
			public:
				void doMember();
				void accept(Visitor *visitor);
		};
		
		class VIP: public Customer {
			public:
				void doVIP();
				void accept(Visitor *visitor);
		};
		
		class VisitorImpl: public Visitor {
			public:
				void visit(Member *member);
				void visit(VIP *vip);
		};

		class Service {
			public:
				void doService(Customer &customer, Query &query, Response &response);
			
			private:
				Visitor *visitor = new VisitorImpl();
		};	
};

#endif
