import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

export default function ViewsClicksChart({ data }) {
  if (!data || data.length === 0) {
    return <div className="h-[300px] flex items-center justify-center text-gray-400">No chart data available</div>;
  }

  return (
    <ResponsiveContainer width="100%" height={300}>
      <LineChart data={data} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
        <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#E5E7EB" />
        <XAxis 
          dataKey="date" 
          axisLine={false} 
          tickLine={false} 
          tick={{ fill: '#6B7280', fontSize: 12 }} 
          dy={10}
        />
        <YAxis 
          axisLine={false} 
          tickLine={false} 
          tick={{ fill: '#6B7280', fontSize: 12 }} 
        />
        <Tooltip 
          contentStyle={{ borderRadius: '12px', border: 'none', boxShadow: '0 4px 6px -1px rgb(0 0 0 / 0.1)' }}
        />
        <Legend iconType="circle" wrapperStyle={{ paddingTop: '20px' }}/>
        <Line 
          type="monotone" 
          name="Views"
          dataKey="views" 
          stroke="#4F46E5" 
          strokeWidth={3} 
          dot={{ r: 4, strokeWidth: 2 }}
          activeDot={{ r: 6 }} 
        />
        <Line 
          type="monotone" 
          name="Clicks"
          dataKey="clicks" 
          stroke="#F59E0B" 
          strokeWidth={3} 
          dot={{ r: 4, strokeWidth: 2 }}
          activeDot={{ r: 6 }} 
        />
      </LineChart>
    </ResponsiveContainer>
  );
}
