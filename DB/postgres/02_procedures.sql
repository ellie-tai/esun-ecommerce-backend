-- Stored function to reserve stock atomically (PostgreSQL)
CREATE OR REPLACE FUNCTION sp_reserve_stock(p_product_id TEXT, p_qty INT) RETURNS BOOLEAN AS $$
DECLARE
  rows_affected INT := 0;
BEGIN
  UPDATE products SET quantity = quantity - p_qty WHERE product_id = p_product_id AND quantity >= p_qty;
  GET DIAGNOSTICS rows_affected = ROW_COUNT;
  RETURN rows_affected = 1;
END;
$$ LANGUAGE plpgsql;
