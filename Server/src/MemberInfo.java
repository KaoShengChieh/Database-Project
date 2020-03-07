public class MemberInfo {
	public int memberID;
	public Membership membership;
	
	MemberInfo() {
		membership = Membership.GUEST;
	}

	MemberInfo(int memberID, Membership membership) {
		this.memberID = memberID;
		this.membership = membership;
	}
}
