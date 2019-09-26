#ifndef HOTEL_MANAGER_H
#define HOTEL_MANAGER_H

#include "database.h"
#include "facade.h"

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

HotelManager::CustomerActionFailException::CustomerActionFailException(std::string msg): errMsg(msg) {}
HotelManager::CustomerActionFailException::~CustomerActionFailException() throw () {}
const char *HotelManager::CustomerActionFailException::what() const throw() { return errMsg.c_str(); }
void HotelManager::Member::doMember() { isMember = true; }
void HotelManager::Member::accept(Visitor *visitor) { visitor->visit(this); }
void HotelManager::VIP::doVIP() { isMember = isVIP = true; }
void HotelManager::VIP::accept(Visitor *visitor) { visitor->visit(this); }
void HotelManager::VisitorImpl::visit(Member *member) { member->doMember(); }
void HotelManager::VisitorImpl::visit(VIP *vip) { vip->doVIP(); }
void HotelManager::connectDBs() {}; //TODO

#endif
