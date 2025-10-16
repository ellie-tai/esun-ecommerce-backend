-- Reserve stock atomically (MSSQL) via stored procedure with OUT parameter
IF OBJECT_ID(N'dbo.sp_reserve_stock', N'P') IS NOT NULL
  DROP PROCEDURE dbo.sp_reserve_stock;
GO

CREATE PROCEDURE dbo.sp_reserve_stock
  @p_product_id VARCHAR(20),
  @p_qty        INT,
  @p_ok         BIT OUTPUT
AS
BEGIN
  SET NOCOUNT ON;

  BEGIN TRAN;

  UPDATE dbo.products
  SET quantity = quantity - @p_qty
  WHERE product_id = @p_product_id
    AND quantity   >= @p_qty;

  IF (@@ROWCOUNT = 1)
  BEGIN
    SET @p_ok = 1;
    COMMIT TRAN;
  END
  ELSE
  BEGIN
    SET @p_ok = 0;
    ROLLBACK TRAN;
  END
END
GO
