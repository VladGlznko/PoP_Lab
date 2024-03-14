with Ada.Text_IO;
procedure Main is
   can_stop : boolean := false;
   pragma Atomic(can_stop);

   task type break_thread;
   task type main_thread is
      entry Set_Values(id : Integer; step: Long_Long_Integer);
   end main_thread;

   task body break_thread is
   begin
      delay 1.0;
      can_stop := true;
   end break_thread;

   task body main_thread is
      sum : Long_Long_Integer := 0;
      counter : Integer := 0;
      local_step: Long_Long_Integer;
      local_id: Integer;

   begin
      accept Set_Values(id : Integer; step: Long_Long_Integer) do
         local_id := id;
         local_step := step;
      end Set_Values;
      loop
         sum := sum + local_step;
         counter := counter + 1;
         exit when can_stop;
      end loop;
      delay 1.0;

      Ada.Text_IO.Put_Line("Id:" & local_id'Img & " Sum:" & sum'Img & " Counter:" & counter'Img & " Step:" & local_step'Img);
   end main_thread;

   b1 : break_thread;
   threads : array (0..14) of main_thread;
   begin
   for I in threads'Range loop
      threads(I).Set_Values(I + 1, Long_Long_Integer(I + 1));
   end loop;
end Main;
