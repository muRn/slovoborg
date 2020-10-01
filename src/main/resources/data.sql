-- thanks https://hipsum.co

insert into Word(id, word)
values(hibernate_sequence.nextval, 'Letterpress');

insert into Word(id, word)
values(hibernate_sequence.nextval, 'Venmo');

insert into Word(id, word)
values(hibernate_sequence.nextval, 'PBR&B');

insert into Word(id, word)
values(hibernate_sequence.nextval, 'Keytar');

insert into Definition(id, word_id, definition, example, submitted_by, submitted_on, likes, dislikes, approved)
values(hibernate_sequence.nextval, 1, 'I''m baby letterpress celiac chicharrones chillwave chartreuse kale chips godard prism. Put a
bird on it echo park ennui shoreditch, lumbersexual shaman mustache brooklyn jianbing. Disrupt succulents tattooed cray
kale chips +1 austin tbh XOXO vice meditation hot chicken lomo synth PBR&B.', 'You probably haven''t heard of them tilde
 flannel yr, forage mlkshk succulents pitchfork kickstarter craft beer blog flexitarian chambray VHS viral. YOLO art
 party chillwave locavore, forage iceland live-edge beard brunch.', 'flexitarian', parsedatetime('30-09-2020',
 'dd-MM-yyyy'), 14, 88, 1);

insert into Definition(id, word_id, definition, example, submitted_by, submitted_on, likes, dislikes, approved)
values(hibernate_sequence.nextval, 2, 'Venmo roof party enamel pin shaman wolf la croix truffaut flexitarian hoodie squid palo santo
mustache XOXO. Unicorn seitan godard mlkshk, craft beer YOLO snackwave flannel bushwick polaroid. Vinyl food truck
chillwave, sartorial disrupt literally waistcoat salvia.', 'Meh hell of crucifix, taiyaki narwhal hella direct trade
neutra austin master cleanse tumblr prism palo santo. La croix palo santo live-edge kickstarter mustache.', 'bushwick',
parsedatetime('01-10-2020', 'dd-MM-yyyy'), 13, 37, 1);

insert into Definition(id, word_id, definition, example, submitted_by, submitted_on, likes, dislikes, approved)
values(hibernate_sequence.nextval, 2, 'Bitters synth venmo swag kitsch ethical, green juice tumeric godard gentrify raclette live-edge
gluten-free. Glossier lumbersexual chillwave prism, cardigan wayfarers cray bitters DIY tryhard af distillery heirloom.
Vaporware trust fund leggings gentrify, copper mug vice viral sriracha pickled XOXO cardigan slow-carb.', 'Chartreuse
trust fund you probably haven''t heard of them edison bulb put a bird on it wolf subway tile humblebrag photo booth.
Waistcoat photo booth organic la croix hammock venmo gluten-free activated charcoal viral taxidermy cray paleo drinking
vinegar. Pork belly adaptogen semiotics truffaut. Tryhard beard flannel literally book marfa.', 'intelligentsia',
parsedatetime('01-10-2020', 'dd-MM-yyyy'), 322, 228, 1);

insert into Definition(id, word_id, definition, example, submitted_by, submitted_on, likes, dislikes, approved)
values(hibernate_sequence.nextval, 3, 'Thundercats master cleanse twee messenger bag retro PBR&B 8-bit iPhone pickled crucifix occupy tote
bag literally letterpress. Dreamcatcher flexitarian pitchfork, waistcoat copper mug gastropub hoodie neutra hexagon
kinfolk vaporware intelligentsia hammock. YOLO portland chia letterpress art party retro.', 'Whatever brunch tofu air
plant heirloom mlkshk migas dreamcatcher tumeric tousled DIY. Shaman la croix etsy readymade gentrify umami pop-up
hoodie poutine freegan fingerstache offal austin slow-carb.', 'marfa', parsedatetime('01-10-2020', 'dd-MM-yyyy'), 0, 0,
0);

insert into Definition(id, word_id, definition, example, submitted_by, submitted_on, likes, dislikes, approved)
values(hibernate_sequence.nextval, 4, 'Keytar unicorn affogato, 8-bit YOLO heirloom pok pok poutine ethical la croix. Pork belly sriracha
forage, freegan affogato cloud bread mustache lo-fi fingerstache prism bitters kitsch iPhone pour-over. Vape freegan
waistcoat hella, flexitarian butcher tumeric pinterest normcore pok pok franzen farm-to-table before they sold out
jianbing.', 'Selvage fanny pack la croix thundercats listicle vegan everyday carry bicycle rights mlkshk. Banjo jean
shorts tofu pug. Poutine tattooed trust fund etsy sartorial.', 'jianbing', parsedatetime('01-10-2020', 'dd-MM-yyyy'), 0,
0, 0);

insert into Opinion(id, definition_id, ip_address, opinion, updated_at)
values(hibernate_sequence.nextval, 5, '127.0.0.1', 1, parsedatetime('30-09-2020 12:34:56', 'dd-MM-yyyy HH:mm:ss'));

insert into Opinion(id, definition_id, ip_address, opinion, updated_at)
values(hibernate_sequence.nextval, 6, '127.0.0.1', -1, parsedatetime('01-10-2020 12:34:56', 'dd-MM-yyyy HH:mm:ss'));