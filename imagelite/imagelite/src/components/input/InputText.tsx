import React from "react";

interface InputTextProps {
  style ?: string;
  placeholder ?: string;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void
}

export const InputText: React.FC<InputTextProps> = ({onChange, style, ...rest}:InputTextProps) => {
  return (
    <input type="text" {...rest} onChange={onChange} className={`${style} border px-3 py-2 rounded-lg text-gray-900`}/>
  )
}