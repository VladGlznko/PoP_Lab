
with Ada.Text_IO; use Ada.Text_IO;
procedure Main is

   dim : constant Integer := 1300;
   thread_num : constant Integer := 12;

   Arr : array (1 .. dim) of Integer;

   type Value_Index is record
      Value : Integer;
      Index : Integer;
   end record;

   procedure Init_Arr is
   begin
      for I in Arr'Range loop
         if I = Dim - Thread_Num then
            Arr(I) := I * (-1);
         else
            Arr(I) := I;
         end if;
      end loop;

      Put_Line("Array initialized successfully");
   end Init_Arr;

   function Part_Min (Start_Index, Finish_Index : in Integer) return Value_Index is
      Min : Value_Index := (Arr(Start_Index), Start_Index);
   begin
      for I in Start_Index .. Finish_Index loop
         if Arr(I) < Min.Value then
            Min.Value := Arr(I);
            Min.Index := I;
         end if;
      end loop;
      return Min;
   end Part_Min;

   task type Starter_Thread is
      entry Start (Start_Index, Finish_Index : in Integer);
   end Starter_Thread;

   protected Part_Manager is
      procedure Set_Part_Min (Min : in Value_Index);
      entry Get_Min (Min : out Value_Index);
   private
      Tasks_Count : Integer := 0;
      Min1 : Value_Index := (Arr(1), 1);
   end Part_Manager;

   protected body Part_Manager is
      procedure Set_Part_Min (Min : in Value_Index) is
      begin
         if Min.Value < Min1.Value then
            Min1 := Min;
         end if;
         Tasks_Count := Tasks_Count + 1;
      end Set_Part_Min;

      entry Get_Min (Min : out Value_Index) when Tasks_Count = Thread_Num is
      begin
         Min := Min1;
      end Get_Min;
   end Part_Manager;

   task body Starter_Thread is
      Min : Value_Index;
      Start_Index, Finish_Index : Integer;
   begin
      accept Start (Start_Index, Finish_Index : in Integer) do
         Starter_Thread.Start_Index := Start_Index;
         Starter_Thread.Finish_Index := Finish_Index;
      end Start;

      Min := Part_Min (Start_Index  => Start_Index,
                       Finish_Index => Finish_Index);
      Part_Manager.Set_Part_Min (Min);
   end Starter_Thread;

   function Parallel_Min return Value_Index is
      Min : Value_Index;
      Thread : array (1 .. Thread_Num) of Starter_Thread;
      Chunk_Size : Integer := Dim / Thread_Num;
      Start_Index : Integer := 1;
      End_Index : Integer;
   begin
      for I in 1 .. (Thread_Num - 1) loop
         End_Index := Start_Index + Chunk_Size;
         Thread(I).Start (Start_Index, End_Index);
         Start_Index := End_Index + 1;
      end loop;

      Thread(Thread_Num).Start (Start_Index, Dim);
      Part_Manager.Get_Min (Min);
      return Min;
   end Parallel_Min;

begin
   Init_Arr;
   declare
      Min_Element : Value_Index := Part_Min (1, Dim);
      Min_Element_Thread : Value_Index := Parallel_Min;
   begin
      Put_Line ("Min Element: " & Min_Element.Value'Image);
      Put_Line ("Min Index: " & Min_Element.Index'Image);
      Put_Line ("Min Element Parallel_Min: " & Min_Element_Thread.Value'Image);
      Put_Line ("Min Index Parallel_Min: " & Min_Element_Thread.Index'Image);
   end;
end Main;
