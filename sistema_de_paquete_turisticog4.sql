-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-11-2021 a las 03:11:07
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_de_paquete_turisticog4`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alojamiento`
--

CREATE TABLE `alojamiento` (
  `idAlojamiento` int(11) NOT NULL,
  `idDestino` int(11) NOT NULL,
  `tipoAlojamiento` varchar(30) NOT NULL,
  `costo` float NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `alojamiento`
--

INSERT INTO `alojamiento` (`idAlojamiento`, `idDestino`, `tipoAlojamiento`, `costo`, `nombre`, `activo`) VALUES
(4047, 2645, 'Hotel', 5970, 'Mar del Plata (MDP)', 1),
(4048, 2645, 'Hotel', 7000, 'Costa Galana (MDP)', 1),
(4049, 2645, 'Departamento', 3550, '1 - uno (MDP)', 1),
(4050, 2645, 'Departamento', 3970, '2 - dos (MDP)', 1),
(4051, 2648, 'Hotel', 7720, 'Santiago de Chile (CH)', 1),
(4052, 2648, 'Hotel', 8500, 'Costa Chile (CH)', 1),
(4053, 2648, 'Departamento', 3330, '1 - uno (CH)', 1),
(4054, 2648, 'Departamento', 3520, '2 - dos (CH)', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `apellido` varchar(40) NOT NULL,
  `dni` int(11) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `nombre`, `apellido`, `dni`, `activo`) VALUES
(2150, 'Lucero', 'Estella', 20265147, 1),
(2151, 'Arce', 'Mariana', 33215335, 1),
(2152, 'Sosa', 'Pedro', 38654780, 1),
(2153, 'Suarez', 'Carlos', 27894457, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `destino`
--

CREATE TABLE `destino` (
  `idDestino` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `pais` varchar(40) NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `destino`
--

INSERT INTO `destino` (`idDestino`, `nombre`, `pais`, `activo`) VALUES
(2645, 'Mar del Plata', 'Argentina', 1),
(2646, 'Cordoba', 'Argentina', 1),
(2647, 'Mendoza', 'Argentina', 1),
(2648, 'Santiago de Chile', 'Chile', 1),
(2649, 'Rio De Janeiro', 'Brazil', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `extraalojamiento`
--

CREATE TABLE `extraalojamiento` (
  `idExtraAlojamiento` int(11) NOT NULL,
  `idAlojamiento` int(11) NOT NULL,
  `tipoDeMenu` varchar(30) NOT NULL,
  `costo` float NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `extraalojamiento`
--

INSERT INTO `extraalojamiento` (`idExtraAlojamiento`, `idAlojamiento`, `tipoDeMenu`, `costo`, `activo`) VALUES
(8838, 4047, 'Pension Completa HT MDP (MPD)', 1750, 1),
(8839, 4047, 'Media Pension HT MDP (MDP)', 1050, 1),
(8840, 4047, 'Sin Pension HT MDP (MDP)', 0, 1),
(8841, 4048, 'Pension Completa HT CG (MDP)', 2350, 1),
(8842, 4048, 'Media Pension HT CG (MDP)', 1350, 1),
(8843, 4048, 'Sin Pension HT CG (MDP)', 0, 1),
(8844, 4049, 'Sin Pension DPTO 1 (MDP)', 0, 1),
(8845, 4050, 'Media Pension DPTO 2 (MDP)', 500, 1),
(8846, 4050, 'Sin Pension DPTO 2 (MDP)', 0, 1),
(8847, 4051, 'Pension Completa HT SDCH (CH)', 1750, 1),
(8848, 4051, 'Media Pension HT SDCH (CH)', 1050, 1),
(8849, 4051, 'Sin Pension HT SDCH (CH)', 0, 1),
(8850, 4052, 'Pension Completa HT CCH (CH)', 2350, 1),
(8851, 4052, 'Media Pension HT CCH (CH)', 1350, 1),
(8852, 4052, 'Sin Pension HT CCH (CH)', 0, 1),
(8853, 4054, 'Media Pension DPTO 2 (CH)', 500, 1),
(8854, 4054, 'Sin Pension DPTO 2 (CH)', 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paquete`
--

CREATE TABLE `paquete` (
  `idPaquete` int(11) NOT NULL,
  `idTransporte` int(11) NOT NULL,
  `idExtraAlojamiento` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `costoTotal` float NOT NULL,
  `fechaEmision` date NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `paquete`
--

INSERT INTO `paquete` (`idPaquete`, `idTransporte`, `idExtraAlojamiento`, `idCliente`, `fechaInicio`, `fechaFin`, `costoTotal`, `fechaEmision`, `activo`) VALUES
(145, 7660, 8839, 2151, '2022-02-20', '2022-02-28', 40900, '2021-10-19', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transporte`
--

CREATE TABLE `transporte` (
  `idTransporte` int(11) NOT NULL,
  `idDestino` int(11) NOT NULL,
  `tipoDeTransporte` varchar(40) NOT NULL,
  `costo` float NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `transporte`
--

INSERT INTO `transporte` (`idTransporte`, `idDestino`, `tipoDeTransporte`, `costo`, `activo`) VALUES
(7660, 2645, 'Avion MDP', 5000, 1),
(7661, 2645, 'Colectivo MDP', 3750, 1),
(7662, 2645, 'Auto Propio MDP', 0, 1),
(7663, 2646, 'Avion CBA', 5500, 1),
(7664, 2646, 'Colectivo CBA', 3250, 1),
(7665, 2646, 'Auto Popio CBA', 0, 1),
(7666, 2647, 'Avion MZA', 7977, 1),
(7667, 2647, 'Colectivo MZA', 3515, 1),
(7668, 2647, 'Auto propio MZA', 0, 1),
(7669, 2648, 'Avion STCH', 7000, 1),
(7670, 2648, 'Colectivo STCH', 3500, 1),
(7671, 2648, 'Auto propio STCH', 0, 1),
(7672, 2649, 'Avion RJ', 9700, 1),
(7673, 2649, 'Colectivo RJ', 5350, 1),
(7674, 2649, 'Auto propio RJ', 0, 1),
(7675, 2649, 'Auto propio RJ', 0, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alojamiento`
--
ALTER TABLE `alojamiento`
  ADD PRIMARY KEY (`idAlojamiento`),
  ADD KEY `idDestino` (`idDestino`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idCliente`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `destino`
--
ALTER TABLE `destino`
  ADD PRIMARY KEY (`idDestino`);

--
-- Indices de la tabla `extraalojamiento`
--
ALTER TABLE `extraalojamiento`
  ADD PRIMARY KEY (`idExtraAlojamiento`),
  ADD KEY `idAlojamiento` (`idAlojamiento`);

--
-- Indices de la tabla `paquete`
--
ALTER TABLE `paquete`
  ADD PRIMARY KEY (`idPaquete`),
  ADD KEY `idTransporte` (`idTransporte`),
  ADD KEY `paquete_ibfk_3` (`idCliente`),
  ADD KEY `paquete_ibfk_2` (`idExtraAlojamiento`);

--
-- Indices de la tabla `transporte`
--
ALTER TABLE `transporte`
  ADD PRIMARY KEY (`idTransporte`),
  ADD KEY `idDestino` (`idDestino`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alojamiento`
--
ALTER TABLE `alojamiento`
  MODIFY `idAlojamiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4055;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2154;

--
-- AUTO_INCREMENT de la tabla `destino`
--
ALTER TABLE `destino`
  MODIFY `idDestino` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2650;

--
-- AUTO_INCREMENT de la tabla `extraalojamiento`
--
ALTER TABLE `extraalojamiento`
  MODIFY `idExtraAlojamiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8855;

--
-- AUTO_INCREMENT de la tabla `paquete`
--
ALTER TABLE `paquete`
  MODIFY `idPaquete` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=146;

--
-- AUTO_INCREMENT de la tabla `transporte`
--
ALTER TABLE `transporte`
  MODIFY `idTransporte` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7676;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alojamiento`
--
ALTER TABLE `alojamiento`
  ADD CONSTRAINT `alojamiento_ibfk_1` FOREIGN KEY (`idDestino`) REFERENCES `destino` (`idDestino`);

--
-- Filtros para la tabla `extraalojamiento`
--
ALTER TABLE `extraalojamiento`
  ADD CONSTRAINT `extraalojamiento_ibfk_1` FOREIGN KEY (`idAlojamiento`) REFERENCES `alojamiento` (`idAlojamiento`);

--
-- Filtros para la tabla `paquete`
--
ALTER TABLE `paquete`
  ADD CONSTRAINT `paquete_ibfk_1` FOREIGN KEY (`idTransporte`) REFERENCES `transporte` (`idTransporte`),
  ADD CONSTRAINT `paquete_ibfk_2` FOREIGN KEY (`idExtraAlojamiento`) REFERENCES `extraalojamiento` (`idExtraAlojamiento`),
  ADD CONSTRAINT `paquete_ibfk_3` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`);

--
-- Filtros para la tabla `transporte`
--
ALTER TABLE `transporte`
  ADD CONSTRAINT `transporte_ibfk_1` FOREIGN KEY (`idDestino`) REFERENCES `destino` (`idDestino`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
