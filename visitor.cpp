#include "hotel_manager.h"

HotelManager::CustomerActionFailException::CustomerActionFailException(std::string msg): errMsg(msg) {}

HotelManager::CustomerActionFailException::~CustomerActionFailException() throw () {}

const char *HotelManager::CustomerActionFailException::what() const throw() { return errMsg.c_str(); }

void HotelManager::Member::doMember() { isMember = true; }

void HotelManager::Member::accept(Visitor *visitor) { visitor->visit(this); }

void HotelManager::VIP::doVIP() { isMember = isVIP = true; }

void HotelManager::VIP::accept(Visitor *visitor) { visitor->visit(this); }

void HotelManager::VisitorImpl::visit(Member *member) { member->doMember(); }

void HotelManager::VisitorImpl::visit(VIP *vip) { vip->doVIP(); }
