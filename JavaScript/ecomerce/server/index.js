import express from "express";
import cors from "cors";
import { shopRouter } from "./routes/shopRouter.js";
import dotenv from "dotenv";
import path from "path";
import { fileURLToPath } from "url";

// Configuración de variables de entorno
dotenv.config();
process.loadEnvFile(".env");

// Necesario para poder usar __dirname en ESModules
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
const corsOption = {
  methods: ["GET", "POST"],
  maxAge: 86400,
};

app.use(cors(corsOption));
app.use(express.json());

// 👇 Servimos la carpeta client como estática
app.use(express.static(path.join(__dirname, "../client")));

// Uso de enrutamiento de la app
app.use("/", shopRouter);

const PORT = process.env.PORT ?? 3000;

app.listen(PORT, () => {
  console.log(
    `El servidor está corriendo en el puerto http://localhost:${PORT}`
  );
});
