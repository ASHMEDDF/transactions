import React from 'react';

const Layout = ({ children }) => (
  <div className="layout">
    <aside className="sidebar">
      <div className="logo">BANCO PICHINCHA</div>
      <nav>
        <ul>
          <li>Clientes</li>
          <li>Cuentas</li>
          <li>Movimientos</li>
          <li>Reportes</li>
        </ul>
      </nav>
    </aside>
    <main className="content">
      {children}
    </main>
  </div>
);

export default Layout;