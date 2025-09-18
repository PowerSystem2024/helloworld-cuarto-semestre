import { Router } from "express";
import { actualizarTarea, crearTarea, eliminarTarea, listarTarea, listarTareas } from "../controllers/tareas.controller";


const router = Router();

router.get("/", listarTareas);

router.get("/:id", listarTarea);

router.post("/", crearTarea);

router.put("/:id", actualizarTarea);

router.delete("/:id", eliminarTarea);

export default router;
