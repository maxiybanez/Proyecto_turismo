-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-10-2021 a las 01:19:52
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.10

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
(1, 1, 'Hotel', 5000, 'Hotel Posadas', 1),
(2, 1, 'departamento', 4000, 'departamento', 1),
(3, 1, 'hostel', 1500, 'hostel paseo', 1),
(4, 2, 'Hotel', 7000, 'Hotel cataratas', 1),
(5, 2, 'departamento', 4000, 'departamento', 1),
(6, 2, 'hostel', 3000, 'hostel iguazu', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `apellido` varchar(40) NOT NULL,
  `dni` float NOT NULL,
  `activo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `nombre`, `apellido`, `dni`, `activo`) VALUES
(1, 'Maxi', 'Ybañez', 37638900, 1),
(3, 'pepe', 'pepito', 1234560, 1);

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
(1, 'Mar del plata', 'Argentina', 1),
(2, 'Cataratas del iguazu', 'Argentina', 1),
(3, 'cancun', 'mexico', 1);

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
(1, 1, 'Pension completa', 1500, 1),
(2, 1, 'Media pension', 1500, 1),
(3, 1, 'sin pension', 0, 1),
(4, 1, 'solo desayuno', 700, 1);

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
(1, 2, 1, 1, '2022-01-03', '2022-01-07', 25000, '2021-10-23', 1);

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
(1, 1, 'Avion', 7500, 1),
(2, 1, 'colectivo', 5000, 1),
(3, 1, 'auto', 3500, 1);

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
  ADD KEY `idExtraAlojamiento` (`idExtraAlojamiento`),
  ADD KEY `paquete_ibfk_3` (`idCliente`);

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
  MODIFY `idAlojamiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `destino`
--
ALTER TABLE `destino`
  MODIFY `idDestino` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `extraalojamiento`
--
ALTER TABLE `extraalojamiento`
  MODIFY `idExtraAlojamiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `paquete`
--
ALTER TABLE `paquete`
  MODIFY `idPaquete` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `transporte`
--
ALTER TABLE `transporte`
  MODIFY `idTransporte` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
