const API = '/zeemart/api/v1';

async function register(name, email, password, role) {
  const res = await fetch(`${API}/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, email, password, role })
  });
  return res.json();
}

async function login(email, password) {
  const res = await fetch(`${API}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
    credentials: 'include'
  });
  return res.json();
}

async function browseProducts(q = '', category = '') {
  const params = new URLSearchParams({ q, category });
  const res = await fetch(`${API}/products?${params}`, { credentials: 'include' });
  return res.json();
}

async function addToCart(productId, quantity) {
  const res = await fetch(`${API}/cart`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    credentials: 'include',
    body: JSON.stringify({ productId, quantity })
  });
  return res.json();
}

async function viewCart() {
  const res = await fetch(`${API}/cart`, { credentials: 'include' });
  return res.json();
}

async function checkout() {
  const res = await fetch(`${API}/orders`, { method: 'POST', credentials: 'include' });
  return res.json();
}
