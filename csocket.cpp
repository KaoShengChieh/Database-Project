#include <cstdio>
#include <cstdlib>
#include <cstring>
//#include <unistd.h>
//#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>

#define CONFIG "./ServerConfig.txt"

class Server {
	public:
		Server();
		~Server(); //close
		ServerThread accept();
	
	private:
		Socket socket;
		hotel_manager hm;
}


class ServerThread: thread

int main( int argc, const char** argv )
{


	/* SOCKET VARIABLES */
	int sock;
	struct sockaddr_in server;
	int mysock;
	char buff[1024];
	int rval;


	/*CREATE SOCKET*/
	sock =socket(AF_INET, SOCK_STREAM, 0);

	if (sock<0) 
	{
	perror("*FAILED TO CREATE SOCKET*");
	exit(1);
	}

	server.sin_family=AF_INET;
	server.sin_addr.s_addr=INADDR_ANY;
	server.sin_port=htons(5000);

	/*CALL BIND*/
	if (bind(sock, (struct sockaddr *)&server, sizeof(server)))
	{
	perror("BIND FAILED");
	exit(1);
	}


	/*LISTEN*/
	listen(sock, 5);


	/*ACCEPT*/
	do{

	mysock= accept(sock, (struct sockaddr *) 0, 0);

	if (mysock==-1) 
	{

	perror ("ACCEPT FAILED");
	}
	else
	{
	memset(buff, 0, sizeof(buff));

	if ((rval=recv(mysock, buff, sizeof(buff), 0)) < 0) 
	perror("READING STREAM MESSAGE ERROR");
	else if(rval==0)
	printf("Ending connection");
	else
	printf("MSG: %s\n", buff);

	printf("GOT THE MESSAGE (rval = %d)\n", rval);

	size_t length, sent;
	length = rval;
	while (length && (sent = send(mysock, buff, length, 0)) > 0) {
	length -= sent;
	}
	if (sent < 0) 
	perror("send");

	}

	}while (1) ;
	return 0; 
}
