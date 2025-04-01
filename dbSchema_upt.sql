CREATE DATABASE IF NOT EXISTS TourManagementSystem;
-- DROP DATABASE TourManagementSystem;
USE TourManagementSystem;

CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NULL,
    date_of_birth DATE NULL,
    profile_picture VARCHAR(255) NULL, 
    role ENUM('traveler', 'admin', 'agent') DEFAULT 'traveler',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Place_Category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Places (
    place_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NULL,
    location VARCHAR(255) NOT NULL,
    latitude DECIMAL(10,8) NULL,
    longitude DECIMAL(11,8) NULL,
    country VARCHAR(100) NOT NULL,
    state VARCHAR(100) NULL,
    city VARCHAR(100) NULL,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES Place_Category(category_id)
);


CREATE TABLE IF NOT EXISTS Place_Images (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    place_id BIGINT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (place_id) REFERENCES Places(place_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Hotels (
    hotel_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT NULL,
    location VARCHAR(255) NOT NULL,
    latitude DECIMAL(10,8) NULL,
    longitude DECIMAL(11,8) NULL,
    country VARCHAR(100) NOT NULL,
    state VARCHAR(100) NULL,
    city VARCHAR(100) NULL,
    price_per_night DECIMAL(10,2) NOT NULL,
    rating DECIMAL(3,2) CHECK (rating BETWEEN 0 AND 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Amenities (
    amenity_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    icon_path VARCHAR(255) NULL
);

CREATE TABLE IF NOT EXISTS Hotel_Amenities (
    hotel_id INT,
    amenity_id INT,
    FOREIGN KEY (hotel_id) REFERENCES Hotels(hotel_id),
    FOREIGN KEY (amenity_id) REFERENCES Amenities(amenity_id),
    PRIMARY KEY (hotel_id, amenity_id)
);

CREATE TABLE IF NOT EXISTS Hotel_Images (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    hotel_id INT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hotel_id) REFERENCES Hotels(hotel_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Flights (
    flight_id INT AUTO_INCREMENT PRIMARY KEY,
    airline VARCHAR(100) NOT NULL,
    flight_number VARCHAR(20) NOT NULL UNIQUE,
    departure_airport VARCHAR(100) NOT NULL,
    arrival_airport VARCHAR(100) NOT NULL,
    departure_city VARCHAR(100) NOT NULL,
    arrival_city VARCHAR(100) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    duration TIME NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    available_seats INT NOT NULL CHECK (available_seats >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Buses (
    bus_id INT AUTO_INCREMENT PRIMARY KEY,
    operator VARCHAR(100) NOT NULL,
    bus_number VARCHAR(20) NOT NULL UNIQUE,
    departure_city VARCHAR(100) NOT NULL,
    arrival_city VARCHAR(100) NOT NULL,
    departure_station VARCHAR(100) NOT NULL,
    arrival_station VARCHAR(100) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    duration TIME NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    available_seats INT NOT NULL CHECK (available_seats >= 0),
    bus_type ENUM('Sleeper', 'Seater', 'Luxury', 'AC', 'Non-AC') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    entity_id INT NOT NULL,  
    entity_type ENUM('Place', 'Hotel', 'Flight', 'Bus') NOT NULL,  
    rating DECIMAL(3,2) NOT NULL CHECK (rating BETWEEN 0 AND 5),  
    review_text TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    entity_id INT NOT NULL,  
    entity_type ENUM('Hotel', 'Flight', 'Bus') NOT NULL,  
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    check_in_date DATETIME NULL,  -- For hotels
    check_out_date DATETIME NULL, -- For hotels
    departure_date DATETIME NULL, -- For flights/buses
    arrival_date DATETIME NULL,   -- For flights/buses
    total_price DECIMAL(10,2) NOT NULL,  
    status ENUM('Pending', 'Confirmed', 'Cancelled', 'Completed') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    user_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method ENUM('Credit Card', 'Debit Card', 'PayPal', 'Net Banking', 'Cash') NOT NULL,
    transaction_id VARCHAR(100) NOT NULL UNIQUE,
    payment_status ENUM('Pending', 'Completed', 'Failed', 'Refunded') DEFAULT 'Pending',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Itinerary (
    itinerary_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('Draft', 'Finalized', 'Completed') DEFAULT 'Draft',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Itinerary_Places (
    itinerary_place_id INT AUTO_INCREMENT PRIMARY KEY,
    itinerary_id INT NOT NULL,
    place_id BIGINT NOT NULL,
    visit_date DATE NOT NULL,
    FOREIGN KEY (itinerary_id) REFERENCES Itinerary(itinerary_id) ON DELETE CASCADE,
    FOREIGN KEY (place_id) REFERENCES Places(place_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Itinerary_Transport (
    itinerary_transport_id INT AUTO_INCREMENT PRIMARY KEY,
    itinerary_id INT NOT NULL,
    transport_type ENUM('Flight', 'Bus', 'Car', 'Train', 'Bike', 'Other') NOT NULL,
    transport_id INT NULL, -- Will reference Flight/Bus if applicable
    departure_location VARCHAR(255) NOT NULL,
    arrival_location VARCHAR(255) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    FOREIGN KEY (itinerary_id) REFERENCES Itinerary(itinerary_id) ON DELETE CASCADE
);


-- Dummy data insertion
-- Insert dummy users
INSERT INTO Users (first_name, last_name, email, password, phone_number, date_of_birth, profile_picture, role)
VALUES 
('Alice', 'Smith', 'alice@example.com', 'hashed_password1', '1234567890', '1990-05-12', NULL, 'traveler'),
('Bob', 'Johnson', 'bob@example.com', 'hashed_password2', '0987654321', '1985-08-25', NULL, 'traveler'),
('Charlie', 'Brown', 'charlie@example.com', 'hashed_password3', '1122334455', '1992-12-10', NULL, 'admin'),
('David', 'Williams', 'david@example.com', 'hashed_password4', '2233445566', '1988-03-05', NULL, 'traveler'),
('Eve', 'Davis', 'eve@example.com', 'hashed_password5', '3344556677', '1995-07-19', NULL, 'agent');

-- Insert dummy place categories
INSERT INTO Place_Category (name) VALUES 
('Historical'),
('Adventure'),
('Nature'),
('Beach'),
('City'),
('Religious'),
('Other');

-- Insert dummy places
-- Assuming PlaceCategory entries:
-- 1: Historical, 2: Adventure, 3: Nature, 4: Beach, 5: City, 6: Religious, 7: Other

-- Insert 5 places
INSERT INTO Places (name, description, location, latitude, longitude, country, state, city, category_id)
VALUES
('Machu Picchu', '15th-century Inca citadel set high in the Andes Mountains in Peru.', 'Machu Picchu, Peru', -13.163141, -72.544963, 'Peru', 'Cusco Region', 'Machu Picchu', 1),
('Queenstown Bungee Jumping', 'Home of the first commercial bungee jump, offering thrilling experiences over the Kawarau River.', 'Kawarau Bridge, Queenstown, New Zealand', -45.027118, 168.662643, 'New Zealand', 'Otago', 'Queenstown', 2),
('Plitvice Lakes National Park', 'A forest reserve in central Croatia known for a chain of 16 terraced lakes, joined by waterfalls.', 'Plitvička Jezera, Croatia', 44.865093, 15.582002, 'Croatia', 'Lika-Senj County', 'Plitvička Jezera', 3),
('Bondi Beach', 'Popular beach known for surfing, golden sand, and vibrant beach culture.', 'Bondi Beach, Sydney, Australia', -33.890842, 151.274292, 'Australia', 'New South Wales', 'Sydney', 4),
('Angkor Wat', 'A temple complex in Cambodia and the largest religious monument in the world.', 'Angkor Wat, Siem Reap, Cambodia', 13.412469, 103.866986, 'Cambodia', 'Siem Reap Province', 'Siem Reap', 6),
('Tokyo Tower', 'An iconic red-and-white communications and observation tower offering panoramic views of Tokyo.', '4 Chome-2-8 Shibakoen, Minato City, Tokyo, Japan', 35.658581, 139.745438, 'Japan', 'Tokyo', 'Minato', 5),
('Museum of the Future', 'Innovative architectural marvel and exhibition space focusing on futuristic technologies.', 'Sheikh Zayed Road, Dubai, UAE', 25.217070, 55.280319, 'United Arab Emirates', 'Dubai', 'Dubai', 7),
('Banff National Park', 'Canada’s oldest national park known for its stunning turquoise lakes and mountainous terrain.', 'Banff, Alberta, Canada', 51.496846, -115.928056, 'Canada', 'Alberta', 'Banff', 3),
('Sossusvlei Sand Dunes', 'Massive red sand dunes in the Namib Desert, perfect for sandboarding and desert hiking.', 'Sossusvlei, Namibia', -24.733020, 15.292300, 'Namibia', 'Hardap', 'Sossusvlei', 2),
('St. Peter\'s Basilica', 'Renaissance-era church in Vatican City, considered one of the holiest Catholic shrines.', 'Piazza San Pietro, Vatican City', 41.902167, 12.453937, 'Vatican City', NULL, 'Vatican City', 6),
('Eiffel Tower', 'Wrought-iron lattice tower on the Champ de Mars, a global cultural icon of France.', 'Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France', 48.858370, 2.294481, 'France', 'Île-de-France', 'Paris', 5),
('Great Wall of China', 'Ancient series of walls and fortifications stretching across northern China.', 'Huairou District, China', 40.431908, 116.570374, 'China', 'Beijing', 'Huairou', 1),
('Grand Canyon', 'Steep-sided canyon carved by the Colorado River, known for its visually overwhelming size.', 'Grand Canyon National Park, Arizona, USA', 36.106965, -112.112997, 'United States', 'Arizona', 'Grand Canyon Village', 3),
('Taj Mahal', 'Ivory-white marble mausoleum and UNESCO World Heritage Site, symbol of eternal love.', 'Dharmapuri, Forest Colony, Tajganj, Agra, Uttar Pradesh, India', 27.175015, 78.042155, 'India', 'Uttar Pradesh', 'Agra', 6),
('Sydney Opera House', 'Multi-venue performing arts centre at Sydney Harbour, known for its unique architecture.', 'Bennelong Point, Sydney NSW 2000, Australia', -33.856784, 151.215297, 'Australia', 'New South Wales', 'Sydney', 7),
('Petra', 'Ancient archaeological city famous for its rock-cut architecture and water conduit system.', 'Ma\'an Governorate, Jordan', 30.328611, 35.441944, 'Jordan', 'Ma\'an', 'Petra', 1),
('Meiji Shrine', 'Shinto shrine dedicated to Emperor Meiji and Empress Shoken, surrounded by a forest.', '1-1 Yoyogikamizonocho, Shibuya City, Tokyo, Japan', 35.676397, 139.699325, 'Japan', 'Tokyo', 'Tokyo', 6),
('Niagara Falls', 'Three massive waterfalls straddling the Canada-USA border, known for powerful flow and scenic views.', 'Niagara Falls, NY, USA / Ontario, Canada', 43.096214, -79.037739, 'United States', 'New York', 'Niagara Falls', 3),
('Burj Khalifa', 'Tallest building in the world, symbolizing modern architecture and luxury in Dubai.', '1 Sheikh Mohammed bin Rashid Blvd, Dubai, UAE', 25.197197, 55.274376, 'United Arab Emirates', 'Dubai', 'Dubai', 5),
('Skydive Dubai', 'Premier skydiving experience over Palm Jumeirah and desert dunes.', 'Al Seyahi St, Dubai Marina, Dubai, UAE', 25.093683, 55.142337, 'United Arab Emirates', 'Dubai', 'Dubai', 2),
('Gardens by the Bay', 'Futuristic nature park featuring the iconic Supertree Grove and biodomes.', '18 Marina Gardens Dr, Singapore', 1.281568, 103.863613, 'Singapore', NULL, 'Singapore', 7),
('Copacabana Beach', 'World-famous beach known for its lively promenade, golden sand, and festive atmosphere.', 'Copacabana, Rio de Janeiro - State of Rio de Janeiro, Brazil', -22.971177, -43.182543, 'Brazil', 'Rio de Janeiro', 'Rio de Janeiro', 4),
('Mount Fuji', 'Iconic volcano and the highest peak in Japan, popular for hiking and scenic beauty.', 'Honshu Island, Japan', 35.360638, 138.727356, 'Japan', 'Yamanashi/Shizuoka', 'Fujinomiya', 3),
('Colosseum', 'Large amphitheatre from ancient Rome, once used for gladiatorial contests.', 'Piazza del Colosseo, 1, 00184 Roma RM, Italy', 41.890251, 12.492373, 'Italy', 'Lazio', 'Rome', 1),
('Golden Temple', 'Holiest shrine of Sikhism, known for its golden dome and serene water tank.', 'Golden Temple Rd, Atta Mandi, Katra Ahluwalia, Amritsar, Punjab, India', 31.620000, 74.876484, 'India', 'Punjab', 'Amritsar', 6),
('Stonehenge', 'Prehistoric monument consisting of a ring of standing stones.', 'Salisbury SP4 7DE, United Kingdom', 51.178882, -1.826215, 'United Kingdom', 'Wiltshire', 'Salisbury', 1),
('Torres del Paine', 'Chilean national park offering rugged mountains, glaciers, and adventure hikes.', 'Magallanes and Chilean Antarctica, Chile', -50.942283, -73.406784, 'Chile', 'Magallanes', 'Puerto Natales', 2),
('Seljalandsfoss', 'Stunning waterfall in Iceland that visitors can walk behind.', 'Þórsmerkurvegur, Iceland', 63.615833, -19.989167, 'Iceland', 'South Region', 'Seljalandsfoss', 3),
('Navagio Beach', 'Secluded beach with crystal-clear waters and a shipwreck, accessible only by boat.', 'Zakynthos, Greece', 37.859211, 20.624928, 'Greece', 'Ionian Islands', 'Zakynthos', 4),
('Times Square', 'Major commercial and entertainment hub in New York City.', 'Manhattan, NY 10036, USA', 40.758896, -73.985130, 'United States', 'New York', 'New York City', 5),
('Lotus Temple', 'Baháʼí House of Worship known for its flowerlike shape.', 'Lotus Temple Rd, Bahapur, Shambhu Dayal Bagh, Kalkaji, New Delhi, India', 28.553492, 77.258826, 'India', 'Delhi', 'New Delhi', 6),
('The Shard', 'Skyscraper in London with observation decks and modern architecture.', '32 London Bridge St, London SE1 9SG, UK', 51.5045, -0.0865, 'United Kingdom', 'England', 'London', 7),
('Chichen Itza', 'Large pre-Columbian archaeological site built by the Maya civilization.', 'Yucatán, Mexico', 20.684285, -88.567783, 'Mexico', 'Yucatán', 'Chichen Itza', 1),
('Hallstatt', 'Charming village by a lake with beautiful alpine views.', 'Hallstatt, Austria', 47.562222, 13.649444, 'Austria', 'Upper Austria', 'Hallstatt', 3),
('Table Mountain', 'Flat-topped mountain overlooking Cape Town, accessible by cable car or hike.', 'Cape Town, South Africa', -33.9628, 18.4098, 'South Africa', 'Western Cape', 'Cape Town', 2),
('The Strip (Las Vegas)', 'Famous stretch in Las Vegas known for casinos, shows, and nightlife.', 'Las Vegas Strip, NV, USA', 36.114647, -115.172813, 'United States', 'Nevada', 'Las Vegas', 5),
('Hagia Sophia', 'Historic mosque and former cathedral and museum in Istanbul.', 'Sultan Ahmet, Ayasofya Meydanı No:1, 34122 Fatih/İstanbul, Turkey', 41.0086, 28.9802, 'Turkey', 'Istanbul', 'Istanbul', 6),
('CN Tower', 'Famous landmark in Toronto offering panoramic views and glass-floor observation deck.', '290 Bremner Blvd, Toronto, ON M5V 3L9, Canada', 43.642566, -79.387057, 'Canada', 'Ontario', 'Toronto', 7),
('Whitehaven Beach', 'Pristine white sand beach on Whitsunday Island, only accessible by boat.', 'Whitsunday Island, Queensland, Australia', -20.2827, 149.0384, 'Australia', 'Queensland', 'Whitsundays', 4),
('Lake Bled', 'Glacial lake in Slovenia with an island church and medieval castle.', 'Bled, Slovenia', 46.3636, 14.0936, 'Slovenia', 'Upper Carniola', 'Bled', 3),
('Acropolis of Athens', 'Ancient citadel containing the remains of several historic buildings, most notably the Parthenon.', 'Athens 105 58, Greece', 37.9715, 23.7267, 'Greece', 'Attica', 'Athens', 1),
('Shrine of Remembrance', 'Monument to Australians who served in war, also a place for remembrance and reflection.', 'Birdwood Ave, Melbourne VIC 3001, Australia', -37.8304, 144.9730, 'Australia', 'Victoria', 'Melbourne', 6),
('Anse Source d\'Argent', 'Famous beach with granite boulders and turquoise waters in Seychelles.', 'La Digue Island, Seychelles', -4.3733, 55.8286, 'Seychelles', NULL, 'La Digue', 4),
('Zhangjiajie National Forest Park', 'Famous for its pillar-like rock formations, inspiring the scenery in Avatar.', 'Wulingyuan District, Hunan, China', 29.3420, 110.4808, 'China', 'Hunan', 'Zhangjiajie', 2),
('Marina Bay Sands', 'Luxury hotel with rooftop infinity pool and observation deck, symbol of modern Singapore.', '10 Bayfront Ave, Singapore 018956', 1.2834, 103.8607, 'Singapore', NULL, 'Singapore', 7),
('Hong Kong Skyline', 'One of the world\'s most impressive urban skylines, especially from Victoria Harbour.', 'Hong Kong Island, Hong Kong', 22.2855, 114.1577, 'China', 'Hong Kong SAR', 'Hong Kong', 5),
('Peyto Lake', 'Glacial lake in Banff National Park, known for its wolf-head shape and turquoise waters.', 'Banff National Park, Alberta, Canada', 51.7167, -116.5225, 'Canada', 'Alberta', 'Banff', 3),
('Alhambra', 'Palace and fortress complex from the Nasrid Dynasty in Granada, Spain.', 'Calle Real de la Alhambra, Granada, Spain', 37.1760, -3.5881, 'Spain', 'Andalusia', 'Granada', 1),
('St. Basil\'s Cathedral', 'Colorful domes and unique architecture make this Russian Orthodox church a symbol of Moscow.', 'Red Square, Moscow, Russia', 55.7525, 37.6231, 'Russia', 'Moscow', 'Moscow', 6),
('Milford Sound', 'Fiord in New Zealand known for dramatic cliffs, waterfalls, and boat cruises.', 'Fiordland National Park, South Island, New Zealand', -44.6414, 167.8970, 'New Zealand', 'Southland', 'Milford Sound', 3),
('Old Quebec', 'Historic district of Quebec City known for its colonial architecture and cobblestone streets.', 'Quebec City, Quebec, Canada', 46.8139, -71.2082, 'Canada', 'Quebec', 'Quebec City', 5),
('Seven Mile Beach', 'Long stretch of golden sand and clear waters in Grand Cayman.', 'Grand Cayman, Cayman Islands', 19.3294, -81.3844, 'Cayman Islands', NULL, 'George Town', 4),
('Moab Arches', 'Home to over 2,000 natural stone arch formations, ideal for hiking and climbing.', 'Arches National Park, Utah, USA', 38.7331, -109.5925, 'United States', 'Utah', 'Moab', 2),
('Himeji Castle', 'Japan\'s most spectacular surviving example of prototypical Japanese castle architecture.', '68 Honmachi, Himeji, Hyogo, Japan', 34.8394, 134.6939, 'Japan', 'Hyogo', 'Himeji', 1),
('Cloud Gate (The Bean)', 'Iconic public sculpture in Chicago\'s Millennium Park reflecting the city skyline.', 'Millennium Park, Chicago, IL, USA', 41.8826, -87.6233, 'United States', 'Illinois', 'Chicago', 7);

-- Insert images for each place (one image per place for simplicity)
INSERT INTO Place_Images (place_id, image_url)
VALUES
(1, 'https://upload.wikimedia.org/wikipedia/commons/e/eb/Machu_Picchu%2C_Peru.jpg'),
(2, 'https://upload.wikimedia.org/wikipedia/commons/f/f6/Bungy_jump_in_Queenstown.jpg'),
(3, 'https://upload.wikimedia.org/wikipedia/commons/9/93/Plitvice_Lakes_National_Park.jpg'),
(4, 'https://upload.wikimedia.org/wikipedia/commons/3/3e/Bondi_Beach%2C_Sydney.jpg'),
(5, 'https://upload.wikimedia.org/wikipedia/commons/a/a6/Angkor_Wat_temple.jpg'),
(6, 'https://upload.wikimedia.org/wikipedia/commons/3/3e/Tokyo_Tower_and_surrounding_area.jpg'),
(7, 'https://upload.wikimedia.org/wikipedia/commons/6/6a/Museum_of_the_Future_Dubai.jpg'),
(8, 'https://upload.wikimedia.org/wikipedia/commons/3/37/Lake_Louise_Banff_National_Park.jpg'),
(9, 'https://upload.wikimedia.org/wikipedia/commons/1/17/Sossusvlei_Dunes_Namibia.jpg'),
(10, 'https://upload.wikimedia.org/wikipedia/commons/0/0e/St_Peter%27s_Basilica_facade%2C_Rome%2C_Italy.jpg'),
(11, 'https://upload.wikimedia.org/wikipedia/commons/a/af/Tour_Eiffel_Wikimedia_Commons.jpg'),
(12, 'https://upload.wikimedia.org/wikipedia/commons/6/6f/GreatWall_2004_Summer_4.jpg'),
(13, 'https://upload.wikimedia.org/wikipedia/commons/5/5e/Grand_Canyon_view_from_Pima_Point_2010.jpg'),
(14, 'https://upload.wikimedia.org/wikipedia/commons/d/da/Taj-Mahal.jpg'),
(15, 'https://upload.wikimedia.org/wikipedia/commons/d/d8/Sydney_Opera_House_-_Dec_2008.jpg'),
(16, 'https://upload.wikimedia.org/wikipedia/commons/e/e5/Al_Khazneh_Petra_Edit_2.jpg'),
(17, 'https://upload.wikimedia.org/wikipedia/commons/6/6d/Meiji_Shrine_main_building.jpg'),
(18, 'https://upload.wikimedia.org/wikipedia/commons/5/5d/Niagara_Falls_from_USA_side.jpg'),
(19, 'https://upload.wikimedia.org/wikipedia/commons/9/93/Burj_Khalifa.jpg'),
(20, 'https://upload.wikimedia.org/wikipedia/commons/2/23/Skydive_Dubai_-_Palm_Jumeirah.jpg'),
(21, 'https://upload.wikimedia.org/wikipedia/commons/f/f2/Gardens_by_the_Bay_in_Singapore_-_20120426.jpg'),
(22, 'https://upload.wikimedia.org/wikipedia/commons/9/9f/Copacabana_beach.jpg'),
(23, 'https://upload.wikimedia.org/wikipedia/commons/1/12/Mount_Fuji_from_Yamanakako.jpg'),
(24, 'https://upload.wikimedia.org/wikipedia/commons/d/d5/Colosseo_2020.jpg'),
(25, 'https://upload.wikimedia.org/wikipedia/commons/0/07/Golden_Temple_Amritsar.jpg'),
(26, 'https://upload.wikimedia.org/wikipedia/commons/3/3c/Stonehenge2007_07_30.jpg'),
(27, 'https://upload.wikimedia.org/wikipedia/commons/d/d8/Torres_del_Paine_National_Park.jpg'),
(28, 'https://upload.wikimedia.org/wikipedia/commons/1/1c/Seljalandsfoss%2C_Iceland.jpg'),
(29, 'https://upload.wikimedia.org/wikipedia/commons/e/ef/Navagio_Beach_-_Zakynthos%2C_Greece.jpg'),
(30, 'https://upload.wikimedia.org/wikipedia/commons/d/d3/Times_Square%2C_New_York_City.jpg'),
(31, 'https://upload.wikimedia.org/wikipedia/commons/1/10/Lotus_temple.jpg'),
(32, 'https://upload.wikimedia.org/wikipedia/commons/f/f2/The_Shard_from_Southwark_London.jpg'),
(33, 'https://upload.wikimedia.org/wikipedia/commons/3/30/Chichen_Itza_El_Castillo.jpg'),
(34, 'https://upload.wikimedia.org/wikipedia/commons/1/11/Hallstatt_Austria.jpg'),
(35, 'https://upload.wikimedia.org/wikipedia/commons/f/f5/Table_Mountain_view_from_Signal_Hill.jpg'),
(36, 'https://upload.wikimedia.org/wikipedia/commons/8/8f/Las_Vegas_Strip_%28Nevada%29.jpg'),
(37, 'https://upload.wikimedia.org/wikipedia/commons/a/a7/Hagia_Sophia_Mars_2013.jpg'),
(38, 'https://upload.wikimedia.org/wikipedia/commons/9/92/CN_Tower_Toronto.jpg'),
(39, 'https://upload.wikimedia.org/wikipedia/commons/9/92/Whitehaven_Beach_Queensland.jpg'),
(40, 'https://upload.wikimedia.org/wikipedia/commons/3/31/Lake_Bled_Slovenia.jpg'),
(41, 'https://upload.wikimedia.org/wikipedia/commons/d/d3/Acropolis_of_Athens.jpg'),
(42, 'https://upload.wikimedia.org/wikipedia/commons/6/64/Shrine_of_Remembrance_Melbourne.jpg'),
(43, 'https://upload.wikimedia.org/wikipedia/commons/3/3f/Anse_Source_d%27Argent_Seychelles.jpg'),
(44, 'https://upload.wikimedia.org/wikipedia/commons/9/9b/Zhangjiajie_National_Forest_Park.jpg'),
(45, 'https://upload.wikimedia.org/wikipedia/commons/0/05/Marina_Bay_Sands_Singapore.jpg'),
(46, 'https://upload.wikimedia.org/wikipedia/commons/1/1b/Hong_Kong_Skyline.jpg'),
(47, 'https://upload.wikimedia.org/wikipedia/commons/e/ef/Peyto_Lake-Banff.jpg'),
(48, 'https://upload.wikimedia.org/wikipedia/commons/6/62/Alhambra_Granada_Spain.jpg'),
(49, 'https://upload.wikimedia.org/wikipedia/commons/d/dd/St_Basils_Cathedral_Red_Square.jpg'),
(50, 'https://upload.wikimedia.org/wikipedia/commons/7/76/Milford_Sound_New_Zealand.jpg'),
(51, 'https://upload.wikimedia.org/wikipedia/commons/b/b7/Old_Quebec.jpg'),
(52, 'https://upload.wikimedia.org/wikipedia/commons/7/7d/Seven_Mile_Beach_Grand_Cayman.jpg'),
(53, 'https://upload.wikimedia.org/wikipedia/commons/6/6a/Delicate_Arch_Arches_National_Park.jpg'),
(54, 'https://upload.wikimedia.org/wikipedia/commons/3/34/Himeji_Castle_Hyogo_Japan.jpg'),
(55, 'https://upload.wikimedia.org/wikipedia/commons/e/e0/Cloud_Gate_Chicago_Bean.jpg');

-- Insert dummy hotels
INSERT INTO Hotels (name, description, location, latitude, longitude, country, state, city, price_per_night, rating)
VALUES 
('Hilton Paris', 'Luxury hotel near Eiffel Tower', 'Paris, France', 48.858093, 2.294694, 'France', 'Ile-de-France', 'Paris', 250.00, 4.5),
('Beijing Grand Hotel', '5-star hotel in Beijing', 'Beijing, China', 39.904211, 116.407395, 'China', NULL, 'Beijing', 180.00, 4.2),
('Canyon Lodge', 'Scenic stay near Grand Canyon', 'Arizona, USA', 36.106965, -112.112997, 'USA', 'Arizona', 'Grand Canyon', 200.00, 4.8),
('Taj View Hotel', 'Stay near Taj Mahal', 'Agra, India', 27.175144, 78.042142, 'India', 'Uttar Pradesh', 'Agra', 150.00, 4.3),
('Sydney Harbour Hotel', 'Hotel with Opera House view', 'Sydney, Australia', -33.856784, 151.215297, 'Australia', 'New South Wales', 'Sydney', 300.00, 4.7);

INSERT INTO Amenities (name, icon_path) VALUES 
('Free Wi-Fi', '/icons/wifi.svg'),
('Swimming Pool', '/icons/pool.svg'),
('Gym', '/icons/gym.svg'),
('Spa', '/icons/spa.svg'),
('Restaurant', '/icons/restaurant.svg'),
('Bar', '/icons/bar.svg'),
('Parking', '/icons/parking.svg'),
('Air Conditioning', '/icons/ac.svg'),
('Room Service', '/icons/room_service.svg'),
('Pet Friendly', '/icons/pet_friendly.svg'),
('Airport Shuttle', '/icons/shuttle.svg'),
('Laundry Service', '/icons/laundry.svg'),
('24/7 Front Desk', '/icons/front_desk.svg'),
('Non-Smoking Rooms', '/icons/no_smoking.svg'),
('Wheelchair Accessible', '/icons/wheelchair.svg'),
('Breakfast Included', '/icons/breakfast.svg');

INSERT INTO Hotel_Amenities (hotel_id, amenity_id) VALUES
(1, 1),  -- Free Wi-Fi
(1, 2),  -- Swimming Pool
(1, 3),  -- Spa
(1, 5),  -- Restaurant
(1, 6),  -- Bar
(1, 8),  -- Air Conditioning
(1, 9),  -- Room Service
(1, 13); -- 24/7 Front Desk

INSERT INTO Hotel_Amenities (hotel_id, amenity_id) VALUES
(2, 1),
(2, 4),  -- Gym
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 11),
(2, 14);

INSERT INTO Hotel_Amenities (hotel_id, amenity_id) VALUES
(3, 1),
(3, 5),
(3, 7),
(3, 8),
(3, 10),
(3, 12),
(3, 13),
(3, 16);

INSERT INTO Hotel_Amenities (hotel_id, amenity_id) VALUES
(4, 1),
(4, 4),
(4, 7),
(4, 8),
(4, 9),
(4, 12),
(4, 13),
(4, 14);

INSERT INTO Hotel_Amenities (hotel_id, amenity_id) VALUES
(5, 1),
(5, 2),
(5, 5),
(5, 6),
(5, 8),
(5, 9),
(5, 10),
(5, 16);

-- Insert dummy hotel images
INSERT INTO Hotel_Images (hotel_id, image_url)
VALUES 
(1, 'hilton_paris.jpg'),
(2, 'beijing_grand.jpg'),
(3, 'canyon_lodge.jpg'),
(4, 'taj_view.jpg'),
(5, 'sydney_harbour.jpg');

-- Insert dummy flights
INSERT INTO Flights (airline, flight_number, departure_airport, arrival_airport, departure_city, arrival_city, departure_time, arrival_time, duration, price, available_seats)
VALUES 
('Air France', 'AF123', 'CDG', 'PEK', 'Paris', 'Beijing', '2025-03-10 08:00:00', '2025-03-10 18:00:00', '10:00:00', 600.00, 50),
('American Airlines', 'AA456', 'JFK', 'PHX', 'New York', 'Phoenix', '2025-03-15 09:30:00', '2025-03-15 14:30:00', '05:00:00', 350.00, 30),
('Qantas', 'QF789', 'SYD', 'LAX', 'Sydney', 'Los Angeles', '2025-03-20 22:00:00', '2025-03-20 14:00:00', '14:00:00', 900.00, 25),
('Lufthansa', 'LH101', 'FRA', 'DEL', 'Frankfurt', 'Delhi', '2025-03-25 06:00:00', '2025-03-25 18:30:00', '12:30:00', 700.00, 40),
('IndiGo', '6E303', 'DEL', 'BOM', 'Delhi', 'Mumbai', '2025-03-30 12:00:00', '2025-03-30 14:30:00', '02:30:00', 100.00, 60);

-- Insert dummy reviews
INSERT INTO Reviews (user_id, entity_id, entity_type, rating, review_text)
VALUES 
(1, 1, 'Place', 4.8, 'Breathtaking view from the top!'),
(2, 2, 'Place', 4.7, 'Amazing piece of history.'),
(3, 3, 'Hotel', 4.5, 'Excellent service and location.'),
(4, 4, 'Flight', 4.2, 'Smooth flight, good service.'),
(5, 5, 'Bus', 3.8, 'Comfortable journey, but a bit late.');

-- Insert dummy bookings
INSERT INTO Bookings (user_id, entity_id, entity_type, check_in_date, check_out_date, departure_date, arrival_date, total_price, status)
VALUES 
(1, 1, 'Hotel', '2025-04-01', '2025-04-05', NULL, NULL, 1250.00, 'Confirmed'),
(2, 2, 'Flight', NULL, NULL, '2025-04-10 08:00:00', '2025-04-10 18:00:00', 600.00, 'Confirmed'),
(3, 3, 'Bus', NULL, NULL, '2025-04-15 09:00:00', '2025-04-15 12:00:00', 50.00, 'Pending'),
(4, 4, 'Hotel', '2025-05-01', '2025-05-03', NULL, NULL, 300.00, 'Completed'),
(5, 5, 'Flight', NULL, NULL, '2025-05-10 22:00:00', '2025-05-11 08:00:00', 800.00, 'Cancelled');

-- Insert dummy payments
INSERT INTO Payments (booking_id, user_id, amount, payment_method, transaction_id, payment_status)
VALUES 
(1, 1, 1250.00, 'Credit Card', 'TXN12345', 'Completed'),
(2, 2, 600.00, 'PayPal', 'TXN67890', 'Completed'),
(3, 3, 50.00, 'Debit Card', 'TXN11223', 'Pending'),
(4, 4, 300.00, 'Net Banking', 'TXN44556', 'Completed'),
(5, 5, 800.00, 'Credit Card', 'TXN77889', 'Refunded');

SELECT TABLE_NAME, CONSTRAINT_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE REFERENCED_TABLE_NAME = 'Places' AND REFERENCED_COLUMN_NAME = 'place_id';

ALTER TABLE itinerary_places DROP FOREIGN KEY itinerary_places_ibfk_2;

ALTER TABLE Places MODIFY COLUMN place_id BIGINT AUTO_INCREMENT NOT NULL;

ALTER TABLE itinerary_places MODIFY COLUMN place_id BIGINT NOT NULL;
ALTER TABLE Place_Images MODIFY COLUMN place_id BIGINT NOT NULL;

ALTER TABLE itinerary_places
ADD CONSTRAINT itinerary_places_ibfk_2 FOREIGN KEY (place_id)
REFERENCES Places(place_id) ON DELETE CASCADE;

SELECT p.place_Id, p.name, p.description, p.location, p.country, p.state, p.city, pi.image_url
FROM places AS p
LEFT JOIN Place_Images AS pi ON p.place_Id = pi.place_id;





