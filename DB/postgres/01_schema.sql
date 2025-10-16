-- PostgreSQL schema for esun demo
CREATE TABLE IF NOT EXISTS products(
  product_id TEXT PRIMARY KEY,
  product_name TEXT NOT NULL,
  price INT NOT NULL,
  quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS orders(
  order_id TEXT PRIMARY KEY,
  member_id TEXT NOT NULL,
  total_price INT NOT NULL,
  pay_status SMALLINT NOT NULL DEFAULT 0,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS order_items(
  order_item_sn BIGSERIAL PRIMARY KEY,
  order_id TEXT NOT NULL REFERENCES orders(order_id),
  product_id TEXT NOT NULL REFERENCES products(product_id),
  quantity INT NOT NULL,
  unit_price INT NOT NULL,
  item_price INT NOT NULL
);
