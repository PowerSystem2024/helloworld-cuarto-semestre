import express from "express";
import morgan from "morgan";
import tareasRoutes from "./router/tareas.routes.js";
import authRoutes from "./router/auth.routes.js";

const app = express();

app.use(morgan("dev"));
app.use(express.urlencoded({ extended: false }));
app.use(express.json());

app.get("/", (req, res) => {
  res.send("Bienvenidos a mi proyecto 🚀");
});

app.use("/api", tareasRoutes);
app.use("/api", authRoutes);

app.get("/test", (req, res) => {
  res.json({ message: "Test endpoint is working!" });
});

// Middleware 404
app.use((req, res, next) => {
  res.status(404).json({ message: "Endpoint not found" });
});

// Middleware 500
app.use((err, req, res, next) => {
  res.status(500).json({
    status: "error",
    message: err.message,
  });
});

export default app;
