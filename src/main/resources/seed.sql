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
