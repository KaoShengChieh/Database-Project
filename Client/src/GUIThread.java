import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUIThread extends Thread {
	private Packet packet;
	private CheckReservation checkreservation;
	private PayReservation payreservation;
	private ListHistory listhistory;
	private JPanel contentPane;
	private int lastPage;
	private CardLayout card = new CardLayout();
	
	public GUIThread(Packet packet) {
		this.packet = packet;
	}

	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ParentFrame GUIFrame = new ParentFrame();
				GUIFrame.setVisible(true);
			}
		});
	}

	private class ParentFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		
		private int xx;
		private int xy;
		
		ParentFrame frameOwner = this;

		public ParentFrame() {
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(150, 50, 984, 603);
			setUndecorated(true);

			contentPane = new JPanel();
			contentPane.setLayout(card);

			Welcome welcome = new Welcome();
			SignIn signin = new SignIn();
			CreateAccount createaccount = new CreateAccount();
			Menu menu = new Menu();
			SearchHotel searchhotel = new SearchHotel();
			OrderHotel orderhotel = new OrderHotel();
			checkreservation = new CheckReservation();
			CancelReservation cancelreservation = new CancelReservation();
			ShowSearchHotel showsearchhotel = new ShowSearchHotel();
			ModifyReservation modifyreservation = new ModifyReservation();
			ShoppingCart shoppingcart = new ShoppingCart();
			payreservation = new PayReservation();
			listhistory = new ListHistory();

			this.add(contentPane);
			contentPane.add(welcome, "1");
			contentPane.add(signin, "2");
			contentPane.add(createaccount, "3");
			contentPane.add(menu, "4"); // having connection with shoppingcart
			contentPane.add(searchhotel, "5");
			contentPane.add(orderhotel, "6"); // having connection with shoppingcart
			contentPane.add(checkreservation, "7"); // having connection with shoppingcart
			contentPane.add(cancelreservation, "8"); // having connection with shoppingcart
			contentPane.add(showsearchhotel, "9");
			contentPane.add(modifyreservation, "10");// having connection with shoppingcart
			contentPane.add(shoppingcart, "11");
			contentPane.add(payreservation, "12");
			contentPane.add(listhistory, "13");

			card.show(contentPane, "1");

//			showsearchhotel.msh_searchre.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					Query query = new Query();
//					query.type = QueryType.LISTORDER;
//					query.userName = userName;
//					
//					Response response = makeQuery(query);
//					if (response.isSuccess) {
//						checkreservation.makeOrderList(response.orderList);
//					} else {
//						ResultPopUp a = new ResultPopUp();
//						a.show(response.getErrorMessage());
//					}
//					/**
//					 * add option for last page, remember last page  is search page = 7 
//					 */
//					lastPage = 7;
//					card.show(contentPane,"7");			
//				}
//			});
			showsearchhotel.showsh_signin.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "2");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 9;
				}
			});
			showsearchhotel.showsh_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 9;
				}
			});
			showsearchhotel.showsh_sort.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					switch (showsearchhotel.showsh_combobox.getSelectedIndex()) {
					default:
					case 0:
						sortHotelInfoList(showsearchhotel.hotelList, "PRICE");
						break;
					case 1:
						sortHotelInfoList(showsearchhotel.hotelList, "STAR");
						break;
					case 2:
						sortHotelInfoList(showsearchhotel.hotelList, "ADDRESS");
					}
					showsearchhotel.makeHotellist();
				}
			});
			/**
			 * 12/14 add to Menu
			 */
			showsearchhotel.lblMenu.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "4");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 9;
				}
			});
			showsearchhotel.showsh_book.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					ResultPopUp a = new ResultPopUp(frameOwner, true);
					if (showsearchhotel.table.getSelectionModel().isSelectionEmpty()) {
						a.show("Select a row to book");
						return;
					}
					
					orderhotel.lblHotelID.setText(showsearchhotel.table.getValueAt(showsearchhotel.table.getSelectedRow(), 0).toString());
					orderhotel.lblAddress.setText(showsearchhotel.table.getValueAt(showsearchhotel.table.getSelectedRow(), 2).toString());
					orderhotel.txtCheckin.setText(searchhotel.sh_datecheckin.getText());
					orderhotel.txtCheckout.setText(searchhotel.sh_datecheckout.getText());
					orderhotel.txt_singleroom.setValue(searchhotel.searchhotel_single.getValue());
					orderhotel.txt_doubleroom.setValue(searchhotel.searchhotel_double.getValue());
					orderhotel.txt_quadroom.setValue(searchhotel.searchhotel_quad.getValue());
					card.show(contentPane, "6");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 9;
				}
			});
			/**
			 * 12/15 add to last page
			 */
			showsearchhotel.lblLastPage.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "5");
					lastPage = 9;
				}
			});
			/**
			 * 12/15 add last page record
			 */
			cancelreservation.cr_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					lastPage = 8;
				}
			});

//			cancelreservation.cr_sr.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					Query query = new Query();
//					query.type = QueryType.LISTORDER;
//					query.userName = userName;
//					
//					Response response = makeQuery(query);
//					if (response.isSuccess) {
//						checkreservation.makeOrderList(response.orderList);
//					} else {
//						ResultPopUp a = new ResultPopUp();
//						a.show(response.getErrorMessage());
//					}
//					card.show(contentPane,"7");
//				}
//			});

			/**
			 * 12/14 add to last page
			 */
			cancelreservation.lblLastPage.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "7");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 8;
				}
			});
			/**
			 * 12/14 add to menu
			 */
			cancelreservation.lblMenu.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "4");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 8;
				}
			});
			cancelreservation.cr_ok.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					ResultPopUp b = new ResultPopUp(frameOwner, true);
					Query query = new Query(QueryType.CANCEL);
					query.additionalInfo = cancelreservation.lblBookID.getText();

//					query.type = QueryType.CANCEL;
//					query.userName = userName;
					/*******/
//					query.bookID = cancelreservation.lblBookID.getText();
					/*******/

					Response response = makeQuery(query);
					if (response.isSuccessful) {
						b.show("Cancel reservation successfully.");
						query = new Query(QueryType.LIST_RESERVATION);
//						query.userName = userName;

						response = makeQuery(query);
						if (response.isSuccessful) {
							checkreservation.makeOrderList(response.orderInfoList);
						} else {
							ResultPopUp a = new ResultPopUp(frameOwner, true);
							a.show(response.getErrorMessage());
						}
						card.show(contentPane, "7");
						/**
						 * 12/15 add last page record
						 */
						lastPage = 8;
					} else {
						b.show(response.getErrorMessage());
					}
				}
			});

			modifyreservation.cr_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 10;
				}
			});

//			modifyreservation.cr_sr.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					Query query = new Query();
//					query.type = QueryType.LISTORDER;
//					query.userName = userName;
//					
//					Response response = makeQuery(query);
//					if (response.isSuccess) {
//						checkreservation.makeOrderList(response.orderList);
//					} else {
//						ResultPopUp a = new ResultPopUp();
//						a.show(response.getErrorMessage());
//					}
//					card.show(contentPane,"7");
//				}
//			});

			/**
			 * 12/14 add last page
			 */
			modifyreservation.lblLastPage.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "7");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 10;
				}
			});
			/**
			 * 12/14 add menu
			 */
			modifyreservation.lblMenu.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "4");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 10;
				}
			});
			modifyreservation.cr_ok.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					ResultPopUp b = new ResultPopUp(frameOwner, true);
					Query query = new Query(QueryType.MODIFY);

//					query.type = QueryType.MODIFY;
//					query.userName = userName;

					try {

						query.orderInfo.bookID = modifyreservation.lblBookID.getText();
						query.orderInfo.checkin = modifyreservation.txtDateFrom.getText();
						query.orderInfo.checkout = modifyreservation.txtDateTo.getText();

						modifyreservation.txt_singleroom.commitEdit();
						modifyreservation.txt_doubleroom.commitEdit();
						modifyreservation.txt_quadroom.commitEdit();

						query.orderInfo.roomNum[0] = (Integer) (modifyreservation.txt_singleroom.getValue());
						query.orderInfo.roomNum[1] = (Integer) (modifyreservation.txt_doubleroom.getValue());
						query.orderInfo.roomNum[2] = (Integer) (modifyreservation.txt_quadroom.getValue());
					} catch (ParseException e) {
						b.show("Invalid input");
						return;
					}

					Response response = makeQuery(query);
					if (response.isSuccessful) {
						b.show("Modify reservation successfully.");
						query = new Query(QueryType.LIST_RESERVATION);
//						query.type = QueryType.LIST_ORDER;
//						query.userName = userName;

						response = makeQuery(query);
						if (response.isSuccessful) {
							checkreservation.makeOrderList(response.orderInfoList);
						} else {
							ResultPopUp a = new ResultPopUp(frameOwner, true);
							a.show(response.getErrorMessage());
						}
						card.show(contentPane, "7");
						/**
						 * 12/15 add last page record
						 */
						lastPage = 10;
					} else {
						b.show(response.getErrorMessage());
					}
				}
			});
			checkreservation.sr_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 7;
				}
			});
			checkreservation.lblMenu.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "4");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 7;
				}
			});
			checkreservation.sh_ok.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					ResultPopUp a = new ResultPopUp(frameOwner, true);
					
					if (checkreservation.text_BookID.getText().equals("Select Book ID")) {
						a.show("Please select a Book ID");
						return;
					}
					
					List<OrderInfo> orderList = checkreservation.orderList;
					OrderInfo orderToModify = null;

					try {
						for (OrderInfo order : orderList) {
							if (order.bookID.equals(checkreservation.text_BookID.getText())) {
								orderToModify = order.clone();
								break;
							}
						}
					} catch (NumberFormatException e) {
						// jump to finally block
					} finally {
						if (orderToModify == null) {
							a.show("No such Book ID");
							return;
						}
					}

					if (checkreservation.changeOrder.getSelectedItem().toString().equals("Modify")) {
						modifyreservation.lblBookID.setText(orderToModify.bookID);
						modifyreservation.lblHotelID.setText("Hotel ID:  " + orderToModify.hotelID);
						modifyreservation.txtDateFrom.setText(orderToModify.checkin);
						modifyreservation.txtDateTo.setText(orderToModify.checkout);
						modifyreservation.txt_singleroom.setValue(orderToModify.roomNum[0]);
						modifyreservation.txt_doubleroom.setValue(orderToModify.roomNum[1]);
						modifyreservation.txt_quadroom.setValue(orderToModify.roomNum[2]);

						card.show(contentPane, "10");

						lastPage = 7;
					} /**
						 * 12/15 add last page record add show address, HOWEVER... (if we went to search
						 * & book first, hotellist had been built, which works fine and properly over
						 * here) (HOWEVER, without doing search & book in the first place, there is no
						 * hotellist, which gives error)
						 */
					else if (checkreservation.changeOrder.getSelectedItem().toString().equals("Cancel")) {

						cancelreservation.lblBookID.setText(orderToModify.bookID);
						cancelreservation.lblHotelID.setText("Hotel ID:  " + orderToModify.hotelID);
						cancelreservation.lblHotelAddress.setText("Hotel Address:  "+ orderToModify.city + "\u5e02" + orderToModify.address);
						cancelreservation.lblDateFrom.setText("Date from:  " + orderToModify.checkin);
						cancelreservation.lblDateTo.setText("to  " + orderToModify.checkout);
						cancelreservation.lblSingleNumber.setText("Single:  " + orderToModify.roomNum[0]);
						cancelreservation.lblDoubleNumber.setText("Double:  " + orderToModify.roomNum[1]);
						cancelreservation.lblQuadNumber.setText("Quad:  " + orderToModify.roomNum[2]);

						card.show(contentPane, "8");
						/**
						 * 12/15 add last page record
						 */
						lastPage = 7;
						/**
						 * 12/13 when go to shoppingcart via check reservation (click the corresponding
						 * reservation order) the the shopping cart should show information of the
						 * reservation MOST of the content below was copy from cancelreservation above
						 * add show address, HOWEVER... (if we went to search & book first, hotellist
						 * had been built, which works fine and properly over here) (HOWEVER, without
						 * doing search & book in the first place, there is no hotellist, which gives
						 * error)
						 */
					} else {
						shoppingcart.lblBookID.setText(orderToModify.bookID);
						shoppingcart.lblHotelID.setText("Hotel ID:  " + orderToModify.hotelID);
						shoppingcart.lblDateFrom.setText("Date From:  " + orderToModify.checkin);
						shoppingcart.lblDateTo.setText("to  " + orderToModify.checkout);
						shoppingcart.lblSingleNumber.setText("Single:  " + orderToModify.roomNum[0]);
						shoppingcart.lblDoubleNumber.setText("Double:  " + orderToModify.roomNum[1]);
						shoppingcart.lblQuadNumber.setText("Quad:  " + orderToModify.roomNum[2]);

//						shoppingcart.lblHotelAddress.setText("Hotel Address:  :"+ showsearchhotel.hotelList.get(orderToModify.hotelID).address);
//						System.out.println(showsearchhotel.hotelList.get(orderToModify.hotelID).address);

						card.show(contentPane, "12");
						/**
						 * 12/15 add last page record
						 */
						lastPage = 7;

					}
				}
			});
			checkreservation.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					if (checkreservation.table.getSelectionModel().isSelectionEmpty()) {
						checkreservation.text_BookID.setText("Select Book ID");
					} else {
						checkreservation.text_BookID.setText(checkreservation.table
								.getValueAt(checkreservation.table.getSelectedRow(), 0).toString());
					}
				}
			});

			orderhotel.borderhotel_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 6;
				}
			});

			/**
			 * 12/14 add last page
			 */
			orderhotel.lblLastPage.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "9");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 6;
				}
			});
			/**
			 * 12/14 add menu
			 */
			orderhotel.lblMenu.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "4");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 6;
				}
			});

//			orderhotel.moh_sr.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {	
//					Query query = new Query();
//					query.type = QueryType.LISTORDER;
//					query.userName = userName;
//					
//					Response response = makeQuery(query);
//					if (response.isSuccess) {
//						checkreservation.makeOrderList(response.orderList);
//					} else {
//						ResultPopUp a = new ResultPopUp();
//						a.show(response.getErrorMessage());
//					}
//					card.show(contentPane,"7");
//				}
//			});
			orderhotel.borderhotel_ok.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent arg0) {
					ResultPopUp a = new ResultPopUp(frameOwner, true);

					Query query = new Query(QueryType.BOOK);
//					query.type = QueryType.BOOK;
					try {
						query.orderInfo.hotelID = Integer.valueOf(orderhotel.lblHotelID.getText());
						query.orderInfo.checkin = orderhotel.txtCheckin.getText();
						query.orderInfo.checkout = orderhotel.txtCheckout.getText();

						orderhotel.txt_singleroom.commitEdit();
						orderhotel.txt_doubleroom.commitEdit();
						orderhotel.txt_quadroom.commitEdit();

						query.orderInfo.roomNum[0] = (Integer) (orderhotel.txt_singleroom.getValue());
						query.orderInfo.roomNum[1] = (Integer) (orderhotel.txt_doubleroom.getValue());
						query.orderInfo.roomNum[2] = (Integer) (orderhotel.txt_quadroom.getValue());
					} catch (ParseException e) {
						a.show("Invalid input");
						return;
					}

					Response response = makeQuery(query);
					if (response.isSuccessful) {
						OrderInfo result = response.getOrder();
						a.setBounds(420, 270, 550, 300);
						try {
							a.show("[Book ID] " + result.bookID + "\n[Hotel ID] " + result.hotelID
									+ "\n\nNumber of rooms" + "\n[Single]  " + result.roomNum[0]
									+ "    [Double]  " + result.roomNum[1] + "    [Quad]  "
									+ result.roomNum[2] + "\n\nStay for "
									+ countDays(result.checkin, result.checkout) + " days"
									+ "\n[Check in]  " + result.checkin + "    [Check out]  "
									+ result.checkout + "\n\n[Price]  " + result.price
									+ " dollars");
						} catch (Exception e) {
							a.setVisible(false);
						}

						query = new Query(QueryType.LIST_RESERVATION);
//						query.type = QueryType.LIST_ORDER;

						response = makeQuery(query);
						if (response.isSuccessful) {
							checkreservation.makeOrderList(response.orderInfoList);
						} else {
							a = new ResultPopUp(frameOwner, true);
							a.show(response.getErrorMessage());
						}
						card.show(contentPane, "7");
						/**
						 * 12/15 add last page record
						 */
						lastPage = 6;
					} else {
						a.show(response.getErrorMessage());
					}
				}
			});

			searchhotel.bsearchhotel_search.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					ResultPopUp a = new ResultPopUp(frameOwner, true);

					int minStar = (int) (searchhotel.searchhotel_minstarspinner.getValue());
					int maxStar = (int) (searchhotel.searchhotel_maxstarspinner.getValue());

					if (minStar > maxStar) {
						a.show("Set hotel star from low to high");
						return;
					}

					Query query = new Query(QueryType.LIST_HOTEL);
//					query.type = QueryType.LIST_HOTEL;
					try {
						query.orderInfo.checkin = searchhotel.sh_datecheckin.getText();
						query.orderInfo.checkout = searchhotel.sh_datecheckout.getText();
						query.orderInfo.roomNum[0] = (Integer) (searchhotel.searchhotel_single.getValue());
						query.orderInfo.roomNum[1] = (Integer) (searchhotel.searchhotel_double.getValue());
						query.orderInfo.roomNum[2] = (Integer) (searchhotel.searchhotel_quad.getValue());
						query.orderInfo.city = searchhotel.searchhotel_citycombobox.getSelectedItem().toString();
						query.orderInfo.additionalInfo = "Star >= " + minStar + " AND Star <= " + maxStar;

						searchhotel.searchhotel_single.commitEdit();
						searchhotel.searchhotel_double.commitEdit();
						searchhotel.searchhotel_quad.commitEdit();
					} catch (ParseException e) {
						a.show("Invalid input");
						return;
					}

					Response response = makeQuery(query);
					if (response.isSuccessful) {
						int AdCode = response.ADcode;
						if (AdCode != -1) {
							new Ad(ParentFrame.this, AdCode).start();
						}
						
						List<HotelInfo> hotelList = response.hotelInfoList;
						sortHotelInfoList(hotelList, "PRICE");
						showsearchhotel.makeHotellist(hotelList);

						/**
						 * 1/2 because the default showsearchhotel only have hotelID, star, address,
						 * price we have to add singleroom doubleroom quadroom number use the code below
						 * add 3 columns and the corresponding data to jtable
						 * 
						 * TODO : not finish yet
						 */

//						showsearchhotel.table.addColumn("Single Room");
//						showsearchhotel.table.addColumn("Double room");
//						showsearchhotel.table.addColumn("Quad room");

						/**
						 * 12/15 set menu visible and invisible(related to login) in guest mode
						 */
						if (searchhotel.bsearchhotel_signin.isVisible()) {
							showsearchhotel.lblMenu.setVisible(false);
						} else {
							showsearchhotel.lblMenu.setVisible(true);
						}
						card.show(contentPane, "9");
						/**
						 * 12/15 add last page record
						 */
						lastPage = 5;
					} else {
						a.show(response.getErrorMessage());
					}
				}
			});

			searchhotel.bsearchhotel_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 5;
				}
			});

//			searchhotel.msh_searchre.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					Query query = new Query();
//					query.type = QueryType.LISTORDER;
//					query.userName = userName;
//					
//					Response response = makeQuery(query);
//					if (response.isSuccess) {
//						checkreservation.makeOrderList(response.orderList);
//					} else {
//						ResultPopUp a = new ResultPopUp();
//						a.show(response.getErrorMessage());
//					}
//					card.show(contentPane,"7");				
//				}
//			});
//			
			searchhotel.lblMenu.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					/**
					 * 12/15 add last page record
					 */
					lastPage = 5;
					card.show(contentPane, "4");
				}
			});

			searchhotel.bsearchhotel_signin.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					/**
					 * 12/15 add last page record
					 */
					lastPage = 5;
					card.show(contentPane, "2");
				}
			});

			menu.bmenu_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 4;
				}
			});
			menu.lblLastPage.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, String.valueOf(lastPage));
					lastPage = 4;
				}
			});
			menu.bmenu_search_hotel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent arg0) {
					card.show(contentPane, "5");
					lastPage = 4;
				}
			});

			menu.bmenu_check_reservation.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent arg0) {
					Query query = new Query(QueryType.LIST_RESERVATION);
//					query.type = QueryType.LIST_ORDER;

					Response response = makeQuery(query);
					if (response.isSuccessful) {
						checkreservation.makeOrderList(response.orderInfoList);
					} else {
						ResultPopUp a = new ResultPopUp(frameOwner, true);
						a.show(response.getErrorMessage());
					}
					card.show(contentPane, "7");
					lastPage = 4;
				}
			});

			menu.bbook_history.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent arg0) {
					Query query = new Query(QueryType.LIST_ORDER);
//					query.type = QueryType.LIST_ORDER;

					Response response = makeQuery(query);
					if (response.isSuccessful) {
						listhistory.makeHistoryList(response.orderInfoList);
					} else {
						ResultPopUp a = new ResultPopUp(frameOwner, true);
						a.show(response.getErrorMessage());
					}
					card.show(contentPane, "7");
					lastPage = 4;
				}
			});

			createaccount.bcreateaccount_createaccount.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {

					// always clean the warning string when click the button
					createaccount.createaccount_sign.setText("");

					if (createaccount.isCorrect(createaccount.passwordField.getPassword(),
							createaccount.passwordField_1.getPassword())) {
						ResultPopUp b = new ResultPopUp(frameOwner, true);

						Query query = new Query(QueryType.SIGN_UP);
//						query.type = QueryType.SIGN_UP;
						String Membership = null;
						/**
						 * 12/13 change membership from text to combobox delete getText, change to
						 * getSelectItem from combo box add email text and combobox
						 * 
						 * TODO : 1. can not create user due to unknow sign up format(some problem with
						 * email string)
						 * 
						 */
//						if(createaccount.usernameField.getText() )

						/**
						 * get text from a radioButton Group
						 */
						for (Enumeration<AbstractButton> button = createaccount.btnGroup.getElements(); button
								.hasMoreElements();) {
							AbstractButton buttons = button.nextElement();
							if (buttons.isSelected())
								Membership = buttons.getText().toString();
						}
						String username = createaccount.usernameField.getText();
						String realname = createaccount.txtRealName.getText();
						String password = String.valueOf(createaccount.passwordField.getPassword());
						String emailname = String.valueOf(createaccount.textEmail.getText());

						/**
						 * use regular expression to check if a string only have number & alphabet in
						 * order to prevent sql injection
						 */
						boolean formFlag = false;

						if (!username.matches("[a-zA-Z0-9]+"))
							formFlag = true;
						if (!realname.matches("[a-zA-Z0-9 ]+"))
							formFlag = true;// allow space to separate name
						if (!password.matches("[a-zA-Z0-9]+"))
							formFlag = true;
						if (!emailname.matches("[a-zA-Z0-9]+"))
							formFlag = true;

						// all string are alphabet & number
						if (formFlag == false) {
							query.userInfo.userName = username;
							query.userInfo.realName = realname;
							query.userInfo.password = encrypt(password);

							query.userInfo.email = emailname + createaccount.comboEmail.getSelectedItem().toString();
							query.userInfo.membership = Membership;

							Response response = makeQuery(query);
							if (response.isSuccessful && Membership == "MEMBER") {
								b.show("Create account successfully.");
								card.show(contentPane, "1");
								lastPage = 3;
							} else if (response.isSuccessful && Membership == "VIP") {
								payreservation.lblTitle.setText("Pay for VIP - Credit Card");
								payreservation.type = PayReservation.payType.FOR_VIP;
								card.show(contentPane, "12");
								lastPage = 3;
							} else {
								b.show(response.getErrorMessage());
							}
						} else {
							if (formFlag == true)
								createaccount.createaccount_sign
										.setText("Can only use number and alphabet for Username, password and Email");
							else
								createaccount.createaccount_sign.setText("Passwords didn't match. Try again.");
						}
					} else {

					}
				}
			});

			createaccount.bcreateaccount_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					lastPage = 3;
				}
			});
			signin.bsignin_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					lastPage = 2;
				}
			});
			signin.lblLastPage.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, String.valueOf(lastPage));
					lastPage = 2;
				}
			});
			signin.bsignin_signin.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					ResultPopUp b = new ResultPopUp(frameOwner, true);

					Query query = new Query(QueryType.LOG_IN);
//					query.type = QueryType.LOG_IN;

					String username = signin.signin_txt1.getText();
					String password = String.valueOf(signin.signin_passwordField.getPassword());

					if (username.equals("GUEST")) {
						b.show("\"GUEST\" is not a valid username");
					}
					
					if (username.matches("[a-zA-Z0-9]+") && password.matches("[a-zA-Z0-9]+")) {
						query.userInfo.userName = username;
						query.userInfo.password = encrypt(password);
					
						Response response = makeQuery(query);
						if (response.isSuccessful) {
							b.show("Sign in successfully.");
							searchhotel.bsearchhotel_signin.setVisible(false);
							showsearchhotel.showsh_signin.setVisible(false);
							showsearchhotel.showsh_book.setVisible(true);
							/**
							 * 12/15
							 */
							switch (lastPage) {
							case 5: // last page is searchhotel (guest mode login operation)
								card.show(contentPane, "5"); // back to previous searchhotel page
								lastPage = 2;
								break;
							case 9: // last page is showsearchhotel (guest mode login operation)
								showsearchhotel.lblMenu.setVisible(true);
								card.show(contentPane, "9"); // back to previous showsearchhotel page
								lastPage = 2;
								break;
							default: // normal mode login
								card.show(contentPane, "4");
								lastPage = 2;
								break;
							}

						} else {
							b.show(response.getErrorMessage());
						}
					} else {
						b.show("Can only use number and alphabet for Username, password");
					}
				}
			});

			welcome.bwelcome_signin.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					/**
					 * 12/15 add last page record
					 */
					lastPage = 1;
					card.show(contentPane, "2");
				}
			});
			welcome.bwelcome_guest.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					ResultPopUp b = new ResultPopUp(frameOwner, true);

					// *********************************
					Query query = new Query(QueryType.LOG_IN);
					query.userInfo.userName = "GUEST";

//					Query query = new Query();
//					query.type = QueryType.LOG_IN;
//					query.userName = "GUEST";
					// **************************************

					Response response = makeQuery(query);
					if (response.isSuccessful) {
						searchhotel.bsearchhotel_signin.setVisible(true);
						showsearchhotel.showsh_signin.setVisible(true);
						showsearchhotel.showsh_book.setVisible(false);
						/**
						 * 12/15 add option for signin in guest mode
						 */
						searchhotel.lblMenu.setVisible(false);
						/**
						 * 12/15 add last page record
						 */
						lastPage = 1;
						card.show(contentPane, "5");
					} else {
						b.show(response.getErrorMessage());
					}
				}
			});
			welcome.bwelcome_creataccount.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "3");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 1;
				}
			});
			welcome.bwelcome_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					Query query = new Query(QueryType.QUIT);
//					query.type = QueryType.QUIT;
					makeQuery(query);
				}
			});

			/**
			 * 12/13
			 * 
			 */
			shoppingcart.btnPay.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					// move the pay operation to payreservation
					payreservation.lblTitle.setText("Pay Reservation - Credit Card");
					payreservation.type = PayReservation.payType.FOR_HOTEL;
					card.show(contentPane, "12");
					lastPage = 7;
				}
			});

			/**
			 * 12/14 add last page
			 */
			shoppingcart.lblLastPage.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "7");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 11;
				}
			});
			/**
			 * 12/14 add menu
			 */
			shoppingcart.lblMenu.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "4");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 11;
				}
			});
			shoppingcart.cr_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
					/**
					 * 12/15 add last page record
					 */
					lastPage = 11;
				}
			});

			payreservation.btnOK.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {

					ResultPopUp b = new ResultPopUp(frameOwner, true);
					Query query = new Query(QueryType.PAY);

					String num1 = payreservation.creditCardNum1.getText();
					String num2 = payreservation.creditCardNum2.getText();
					String num3 = payreservation.creditCardNum3.getText();
					String num4 = payreservation.creditCardNum4.getText();
					String realname = payreservation.txtRealName.toString();

					if (num1.length() == 4 && isInteger(num1) && num2.length() == 4 && isInteger(num2)
							&& num3.length() == 4 && isInteger(num3) && num4.length() == 4 && isInteger(num4)
							&& realname.matches("[a-zA-Z0-9 ]+")) {

						int userCardNum = Integer.parseInt(payreservation.creditCardNum1.getText()
								+ payreservation.creditCardNum2.getText() + payreservation.creditCardNum3.getText()
								+ payreservation.creditCardNum4.getText());
						String expiration = payreservation.txtYear.toString() + "/"
								+ payreservation.txtMonth.toString();
						String security = payreservation.txtSecurityNum.getText();

						switch (payreservation.type) {
							case FOR_VIP:
								query.orderInfo.bookID = "PAY_FOR_VIP";
								query.userInfo.membership = "VIP";
								break;
							case FOR_HOTEL:
								query.orderInfo.bookID = shoppingcart.lblBookID.getText();
								break;
						}
						
						query.creditCardInfo.ownerName = realname;
						query.creditCardInfo.CS_ID = userCardNum;
						query.creditCardInfo.expirationDate = expiration;
						query.creditCardInfo.securityCode = security;

						Response response = makeQuery(query);

						if (response.isSuccessful) {
							//OrderInfo orderInfo = response.getOrder();
							
							switch (payreservation.type) {
								case FOR_VIP:
									// result popup after vip member was created : username, realname,
									// membership(VIP), card number, expiration date
									b.show("Upgrade successfully, please log in again");
									// createaccount -> welcome
									card.show(contentPane, "1");
									break;
								case FOR_HOTEL:
									/**
									 * result popup after pay reservation : bookid, username, hotelid, room number,
									 * check in& out, price, realname, cardid card date
									 */
//									b.show("[Book ID] " + result.orderInfo.bookID + "    [Username] "
//											+ result.userInfo.userName + "\n[Hotel ID] " + result.orderInfo.hotelID
//											+ "\n\nNumber of rooms" + "\n[Single]  " + result.orderInfo.roomNum[0]
//											+ "    [Double]  " + result.orderInfo.roomNum[1] + "    [Quad]  "
//											+ result.orderInfo.roomNum[2] + "\n\nStay for "
//											+ countDays(result.orderInfo.checkin, result.orderInfo.checkout) + " days"
//											+ "\n[Check in]  " + result.orderInfo.checkin + "    [Check out]  "
//											+ result.orderInfo.checkout + "\n\n[Price]  " + result.orderInfo.price
//											+ " dollars" + " [ RealName ] " + result.creditCardInfo.ownerName
//											+ "\n[Credit card number ]" + result.creditCardInfo.CS_ID
//											+ "\n[ Credit card expiration date ]" + result.creditCardInfo.expirationDate);
									// pay reservation -> go to list history
									listhistory.makeHistoryList(response.orderInfoList);
									card.show(contentPane, "13");
									break;
							}
							lastPage = 12;
						}
					} else {
						b.show("wrong credit card number OR not accepted input format");
					}
				}

			});

			payreservation.bCreditCard_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
				}
			});

			payreservation.lblLastPage.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, String.valueOf(lastPage));
					lastPage = 12;
				}
			});
			payreservation.lblMenu.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "4");
					lastPage = 12;
				}
			});

			listhistory.cr_x.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "1");
				}
			});
			listhistory.lblMenu.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					card.show(contentPane, "4");
					lastPage = 12;
				}
			});

			getContentPane().addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent arg0) {
					xx = arg0.getX();
					xy = arg0.getY();
				}
			});
			getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();
					setLocation(x - xx, y - xy);
				}
			});

		}
	}

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	private Response makeQuery(Query query) {
		long send_time = java.util.Calendar.getInstance().getTimeInMillis();

		packet.setQuery(query);
		System.out.println("Successfully delivered " + query.type.toString() + " query");

		Response response = packet.getResponse();
		System.out.println("Successfully received " + query.type.toString() + " response ("
				+ (java.util.Calendar.getInstance().getTimeInMillis() - send_time) / 1000D + " seconds)");

		return response;
	}

	private void sortHotelInfoList(List<HotelInfo> hotelInfoList, String sortFactor) {
		switch (sortFactor) {
		default:
		case "PRICE":
			Collections.sort(hotelInfoList, new Comparator<HotelInfo>() {
				public int compare(HotelInfo hotel1, HotelInfo hotel2) {
					if (hotel1.price != hotel2.price) {
						return hotel1.price - hotel2.price;
					} else if (hotel2.star != hotel1.star) {
						return hotel2.star - hotel1.star;
					} else {
						return hotel1.city.compareTo(hotel2.city);
					}
				}
			});
			break;
		case "STAR":
			Collections.sort(hotelInfoList, new Comparator<HotelInfo>() {
				public int compare(HotelInfo hotel1, HotelInfo hotel2) {
					if (hotel2.star != hotel1.star) {
						return hotel2.star - hotel1.star;
					} else if (hotel1.price != hotel2.price) {
						return hotel1.price - hotel2.price;
					} else {
						return hotel1.city.compareTo(hotel2.city);
					}
				}
			});
			break;
		case "ADDRESS":
			Collections.sort(hotelInfoList, new Comparator<HotelInfo>() {
				public int compare(HotelInfo hotel1, HotelInfo hotel2) {
					if (hotel1.address.compareTo(hotel2.address) != 0) {
						return hotel1.address.compareTo(hotel2.address);
					} else if (hotel1.price != hotel2.price) {
						return hotel1.price - hotel2.price;
					} else {
						return hotel2.star - hotel1.star;
					}
				}
			});
		}
	}

	private String encrypt(String strToEncrypt) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] messageDigest = md.digest(strToEncrypt.getBytes());

			BigInteger no = new BigInteger(1, messageDigest);

			String encryptText = no.toString(16);

			while (encryptText.length() < 32) {
				encryptText = "0" + encryptText;
			}

			return encryptText;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private int countDays(String D1, String D2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate firstDate = LocalDate.parse(D1, formatter);
		LocalDate secondDate = LocalDate.parse(D2, formatter);

		return (int) ChronoUnit.DAYS.between(firstDate, secondDate);
	}

	public void updateReservation(Response response) {
		System.out.println("Received UPDATE message");
		checkreservation.makeOrderList(response.orderInfoList);
	}
	
	public void updateOrder(Response response) {
		System.out.println("Received UPDATE message");
		listhistory.makeHistoryList(response.orderInfoList);
	}
	
	public void setPayForVIP() {
		payreservation.lblTitle.setText("Upgrade VIP - Credit Card");
		payreservation.type = PayReservation.payType.FOR_VIP;
		card.show(contentPane, "12");
		lastPage = 10;
	}
}
