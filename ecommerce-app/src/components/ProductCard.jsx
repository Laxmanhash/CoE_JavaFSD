import { useCart } from "../context/CartContext";

const products = [
  { id: 1, name: "Laptop", price: 50000 },
  { id: 2, name: "Phone", price: 20000 },
  { id: 3, name: "Headphones", price: 3000 },
];

const ProductCard = () => {
  const { dispatch } = useCart();

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-4 p-6">
      {products.map(product => (
        <div key={product.id} className="border p-4 rounded-lg shadow-lg text-center">
          <h2 className="text-xl font-bold">{product.name}</h2>
          <p className="text-gray-600">₹{product.price}</p>
          <button
            onClick={() => dispatch({ type: "ADD_TO_CART", payload: product })}
            className="bg-green-500 text-white px-4 py-2 mt-2 rounded"
          >
            Add to Cart
          </button>
        </div>
      ))}
    </div>
  );
};

export default ProductCard;
