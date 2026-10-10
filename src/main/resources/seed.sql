-- password for all seed accounts is: Password123!
INSERT INTO users (name, email, password_hash, role)
SELECT 'Admin', 'admin@zeemart.local', '$2a$10$IY0AOO6o.w9z4IIHmM6R3u8RU0Lzm.9V8oIHW8.STqyj6K8JxDq', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='admin@zeemart.local');

INSERT INTO users (name, email, password_hash, role)
SELECT 'Seller One', 'seller1@zeemart.local', '$2a$10$IY0AOO6o.w9z4IIHmM6R3u8RU0Lzm.9V8oIHW8.STqyj6K8JxDq', 'SELLER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='seller1@zeemart.local');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Wireless Mouse', 'Ergonomic 2.4GHz wireless mouse', 599.00, 50, 'Electronics', 'https://example.com/mouse.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Wireless Mouse');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Mechanical Keyboard', '87-key hot-swappable keyboard', 2999.00, 20, 'Electronics', 'https://example.com/kb.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Mechanical Keyboard');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Cotton T-Shirt', 'Plain round-neck cotton t-shirt', 399.00, 100, 'Apparel', 'https://example.com/tshirt.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Cotton T-Shirt');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Bluetooth Headphones', 'Wireless over-ear headphones with 30-hour battery', 1499.00, 35, 'Electronics', 'https://example.com/headphones.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Bluetooth Headphones');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'USB-C Hub', '7-in-1 USB-C hub with HDMI and card reader', 1199.00, 40, 'Electronics', 'https://example.com/hub.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='USB-C Hub');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Power Bank 10000mAh', 'Slim fast-charging power bank', 899.00, 60, 'Electronics', 'https://example.com/powerbank.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Power Bank 10000mAh');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Laptop Backpack', 'Water-resistant backpack for 15.6 inch laptops', 1299.00, 25, 'Accessories', 'https://example.com/backpack.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Laptop Backpack');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Steel Water Bottle', '1 litre insulated stainless steel bottle', 449.00, 80, 'Accessories', 'https://example.com/bottle.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Steel Water Bottle');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Desk Lamp', 'LED desk lamp with 3 brightness levels', 699.00, 30, 'Home', 'https://example.com/lamp.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Desk Lamp');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Ceramic Coffee Mug', '350 ml ceramic mug, microwave safe', 199.00, 120, 'Home', 'https://example.com/mug.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Ceramic Coffee Mug');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Running Shoes', 'Lightweight breathable running shoes', 1999.00, 45, 'Footwear', 'https://example.com/shoes.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Running Shoes');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Denim Jacket', 'Classic blue denim jacket', 1799.00, 22, 'Apparel', 'https://example.com/jacket.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Denim Jacket');

INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url)
SELECT id, 'Notebook Set', 'Pack of 5 ruled A5 notebooks', 249.00, 150, 'Stationery', 'https://example.com/notebook.jpg'
FROM users WHERE email='seller1@zeemart.local'
AND NOT EXISTS (SELECT 1 FROM products WHERE name='Notebook Set');
