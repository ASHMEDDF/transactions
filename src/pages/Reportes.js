import React from 'react';
import Layout from '../components/Layout';

const Reportes = () => {
  return (
    <Layout>
      <h1>Reportes</h1>
      <input type="text" placeholder="Buscar" />
      <button>Nuevo</button>
      {/* vista para generar resportes */}
    </Layout>
  );
};

export default Reportes;