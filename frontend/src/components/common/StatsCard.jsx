export default function StatsCard({ title, value, icon, gradient = 'from-blue-500 to-indigo-500' }) {
  return (
    <div className="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 flex flex-col justify-between overflow-hidden relative group hover:shadow-md transition-shadow">
      <div className="flex justify-between items-start mb-4">
        <h3 className="text-gray-500 font-medium text-sm z-10">{title}</h3>
        {icon && <div className="text-gray-400 z-10 
            group-hover:text-blue-500 transition-colors duration-300">{icon}</div>}
      </div>
      <p className="text-3xl font-bold text-gray-900 z-10">{value}</p>
      
      <div className={`absolute -right-6 -bottom-6 w-24 h-24 rounded-full bg-gradient-to-br ${gradient} opacity-5 group-hover:opacity-10 transition-opacity duration-500`}></div>
    </div>
  );
}
