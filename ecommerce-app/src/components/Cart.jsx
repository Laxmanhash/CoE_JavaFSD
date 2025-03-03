import { useCart } from "../context/CartContext";

const Cart = () => {
  const { cart, dispatch } = useCart();

  const totalPrice = cart.reduce((sum, item) => sum + item.price, 0);

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold">Shopping Cart</h2>
      {cart.length === 0 ? (
        <p className="text-gray-600 mt-4">Your cart is empty.</p>
      ) : (
        <div>
          {cart.map(item => (
            <div key={item.id} className="border p-4 my-2 rounded flex justify-between">
              <span>{item.name} - ₹{item.price}</span>
              <button
                onClick={() => dispatch({ type: "REMOVE_FROM_CART", payload: item.id })}
                className="bg-red-500 text-white px-4 py-1 rounded"
              >
                Remove
              </button>
            </div>
          ))}
          <h3 className="text-xl font-bold mt-4">Total: ₹{totalPrice}</h3>
        </div>
      )}
    </div>
  );
};

export default Cart;
