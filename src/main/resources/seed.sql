-- password for all seed accounts is: Password123!  (bcrypt hash below)
INSERT INTO users (name, email, password_hash, role)
SELECT 'Admin', 'admin@zeemart.local', '$2a$12$8HqjK5r7c1s0m0K5m1z1Y.z8mE0m0m9t9r7t2wYQ1kzq0v9r0f4Sa', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='admin@zeemart.local');

INSERT INTO users (name, email, password_hash, role)
SELECT 'Seller One', 'seller1@zeemart.local', '$2a$12$8HqjK5r7c1s0m0K5m1z1Y.z8mE0m0m9t9r7t2wYQ1kzq0v9r0f4Sa', 'SELLER'
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
