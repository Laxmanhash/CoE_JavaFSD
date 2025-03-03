import { Link } from "react-router-dom";
import { useCart } from "../context/CartContext";

const Navbar = () => {
  const { cart } = useCart();

  return (
    <nav className="bg-blue-500 p-4 flex justify-between text-white">
      <Link to="/" className="text-xl font-bold">E-Commerce</Link>
      <Link to="/cart" className="text-lg">Cart ({cart.length})</Link>
    </nav>
  );
};

export default Navbar;
